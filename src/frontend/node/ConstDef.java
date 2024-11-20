package frontend.node;

import frontend.Visitor;
import frontend.ir.GlobalVariable;
import frontend.ir.constants.Constant;
import frontend.ir.instructions.MemInstructions.alloca;
import frontend.ir.instructions.MemInstructions.store;
import frontend.ir.type.ArrayType;
import frontend.node.constInitVal.ConstInitVal;
import frontend.symbol.Symbol;
import frontend.token.token;
import frontend.tool.myWriter;

public class ConstDef extends node {
    public token ident;
    public token lbrack;
    public ConstExp constExp;
    public token rbrack;

    public token assign;
    public ConstInitVal constInitVal;

    public void print() {
        ident.print();
        if (lbrack != null) {
            lbrack.print();
            constExp.print();
            if (rbrack != null) rbrack.print();
        }
        assign.print();
        constInitVal.print();
        myWriter.writeNonTerminal("ConstDef");
    }

    @Override
    public void visit() {
        //常量是一定可以计算初始值的，在声明时就必须赋值，且无法改变，但是全局常量需要声明出来，局部常量则无需多加处理
        //全局和局部常量加入符号表即可，后续直接从符号表中取出具体的值进行使用
        // 提取关键信息，提高可读性
        String variableName = ident.token(); // 变量名
        boolean isGlobal = Visitor.isGlobal(); // 是否为全局变量
        boolean isArray = (constExp != null); // 是否为数组

        if (!isArray) {
            constInitVal.visit();
            if (isGlobal) { // 全局普通变量
                GlobalVariable globalVariable = new GlobalVariable(variableName, (Constant) Visitor.upValue, true);
                Visitor.model.addGlobalValue(globalVariable);
            }

            Visitor.curTable.addSymbol(new Symbol(variableName, Visitor.upValue));
        } else { // 数组变量
            constExp.visit(); // 处理数组维度
            Visitor.ValueType = new ArrayType(Visitor.ValueType, Visitor.upConstValue); // 构建数组类型

            if (isGlobal) { // 全局数组
                constInitVal.visit(); // 处理数组的初始值
                GlobalVariable globalVariable = new GlobalVariable(variableName, (Constant) Visitor.upValue, true);
                Visitor.model.addGlobalValue(globalVariable);
                Visitor.curTable.addSymbol(new Symbol(variableName, globalVariable));
            } else { // 局部数组
                alloca alloca = new alloca(Visitor.ValueType); // 分配空间
                Visitor.curBlock.addInstruction(alloca);
                Visitor.curTable.addSymbol(new Symbol(variableName, alloca));
                constInitVal.visit(); // 处理数组的初始值
                store store = new store(Visitor.upValue, alloca); // 存储数组初始值
                Visitor.curBlock.addInstruction(store);
            }
        }
    }
}//常量定义ConstDef → Ident [ '[' ConstExp ']' ] '=' ConstInitVal

