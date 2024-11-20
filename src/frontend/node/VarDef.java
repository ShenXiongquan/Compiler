package frontend.node;


import frontend.Visitor;
import frontend.ir.GlobalVariable;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.constants.Constant;
import frontend.ir.constants.Zeroinitializer;
import frontend.ir.instructions.MemInstructions.alloca;
import frontend.ir.instructions.MemInstructions.getelementptr;
import frontend.ir.instructions.MemInstructions.store;
import frontend.ir.type.ArrayType;
import frontend.ir.type.IntegerType;
import frontend.node.initVal.InitVal;
import frontend.symbol.Symbol;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;

public class VarDef extends node {
    public token ident;
    public token lbrack;
    public ConstExp constExp;
    public token rbrack;
    public token assign;
    public InitVal initVal;

    public void print() {
        ident.print();
        if (lbrack != null) {
            lbrack.print();
            constExp.print();
            if (rbrack != null) rbrack.print();
        }
        if (assign != null) {
            assign.print();
            initVal.print();
        }
        myWriter.writeNonTerminal("VarDef");
    }

    @Override
    public void visit() {
        // 提取关键信息，提高可读性
        String variableName = ident.token(); // 变量名
        boolean isGlobal = Visitor.isGlobal(); // 是否为全局变量
        boolean isArray = (constExp != null); // 是否为数组
        boolean initialized = (initVal != null);
        if (!isArray) { // 普通变量

            if (isGlobal) { // 全局普通变量
                if (initialized) {
                    Visitor.calAble = true;
                    initVal.visit();
                    Visitor.calAble = false;
                }
                GlobalVariable globalVariable = new GlobalVariable(variableName, initialized ? (Constant) Visitor.upValue : new ConstInt((IntegerType) Visitor.ValueType, 0), false);
                Visitor.model.addGlobalValue(globalVariable);
                Visitor.curTable.addSymbol(new Symbol(variableName, globalVariable));
            } else { // 局部普通变量
                alloca alloca = new alloca(Visitor.ValueType); // 分配空间
                Visitor.curBlock.addInstruction(alloca);
                Visitor.curTable.addSymbol(new Symbol(variableName, alloca));
                if (initialized) {
                    initVal.visit();
                    store store = new store(Visitor.upValue, alloca); // 存储初始值
                    Visitor.curBlock.addInstruction(store);
                }
            }
        } else { // 数组变量
            constExp.visit(); // 处理数组维度
            Visitor.ValueType = new ArrayType(Visitor.ValueType, Visitor.upConstValue); // 构建数组类型

            if (isGlobal) { // 全局数组
                if (initialized) {
                    Visitor.calAble = true;
                    initVal.visit();
                    Visitor.calAble = false;
                }
                GlobalVariable globalVariable = new GlobalVariable(variableName, initialized ? (Constant) Visitor.upValue : new Zeroinitializer((ArrayType) Visitor.ValueType), false);
                Visitor.model.addGlobalValue(globalVariable);
                Visitor.curTable.addSymbol(new Symbol(variableName, globalVariable));
            } else { // 局部数组
                alloca alloca = new alloca(Visitor.ValueType); // 分配空间
                Visitor.curBlock.addInstruction(alloca);
                Visitor.curTable.addSymbol(new Symbol(variableName, alloca));
                if (initialized) {
                    Visitor.upArrayValue = new ArrayList<>();
                    initVal.visit();
                    int i = 0;
                    for (Value element : Visitor.upArrayValue) {
                        getelementptr getelementptr = new getelementptr(alloca, ConstInt.zero, new ConstInt(IntegerType.i32, i++));
                        Visitor.curBlock.addInstruction(getelementptr);
                        store store = new store(element, getelementptr);
                        Visitor.curBlock.addInstruction(store);
                    }
                }

            }

        }
    }//变量定义 VarDef → Ident [ '[' ConstExp ']' ] | Ident [ '[' ConstExp ']' ] '='InitVal
}