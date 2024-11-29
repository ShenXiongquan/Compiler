package frontend.node;


import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.GlobalVariable;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.constants.Constant;
import frontend.llvm_ir.constants.Zeroinitializer;
import frontend.llvm_ir.instructions.MemInstructions.alloca;
import frontend.llvm_ir.instructions.MemInstructions.getelementptr;
import frontend.llvm_ir.type.ArrayType;
import frontend.llvm_ir.type.IntegerType;
import frontend.node.initVal.InitVal;
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

    public void visit() {
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
                Visitor.curTable.addSymbol(variableName, globalVariable);
            } else { // 局部普通变量
                alloca alloca = alloca(Visitor.ValueType); // 分配空间
                Visitor.curTable.addSymbol(variableName, alloca);
                if (initialized) {
                    initVal.visit();
                    store(Visitor.upValue, alloca); // 存储初始值

                }
            }
        } else { // 数组变量
            constExp.visit(); // 处理数组维度
            Visitor.ArraySize = Visitor.upConstValue;
            ArrayType arrayType = new ArrayType(Visitor.ValueType, Visitor.ArraySize); // 构建数组类型

            if (isGlobal) { // 全局数组
                if (initialized) {
                    Visitor.calAble = true;
                    initVal.visit();
                    Visitor.calAble = false;
                }
                GlobalVariable globalVariable = new GlobalVariable(variableName, initialized ? (Constant) Visitor.upValue : new Zeroinitializer(arrayType), false);
                Visitor.model.addGlobalValue(globalVariable);
                Visitor.curTable.addSymbol(variableName, globalVariable);
            } else { // 局部数组
                alloca alloca = alloca(arrayType); // 分配空间
                Visitor.curTable.addSymbol(variableName, alloca);
                if (initialized) {
                    Visitor.upArrayValue = new ArrayList<>();
                    initVal.visit();
                    int i = 0;
                    for (Value element : Visitor.upArrayValue) {
                        getelementptr getelementptr = getelementptr(alloca, ConstInt.zero, new ConstInt(IntegerType.i32, i++));
                        store(element, getelementptr);
                    }
                }

            }

        }
    }//变量定义 VarDef → Ident [ '[' ConstExp ']' ] | Ident [ '[' ConstExp ']' ] '='InitVal
}