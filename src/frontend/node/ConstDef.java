package frontend.node;

import frontend.llvm_ir.GlobalVar;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.Constant;
import frontend.llvm_ir.instructions.MemInstructions.alloca;
import frontend.llvm_ir.type.ArrayType;
import frontend.node.constInitVal.ConstInitVal;
import frontend.token.token;

public class ConstDef extends node {
    public token ident;
    public token lbrack;
    public ConstExp constExp;
    public token rbrack;
    public token assign;
    public ConstInitVal constInitVal;

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.print());
        if (lbrack != null) {
            sb.append(lbrack.print());
            sb.append(constExp.print());
            if (rbrack != null) {
                sb.append(rbrack.print());
            }
        }
        sb.append(assign.print());
        sb.append(constInitVal.print());
        sb.append("<ConstDef>\n");
        return sb.toString();
    }


    public void visit() {
        //常量是一定可以计算初始值的，在声明时就必须赋值，且无法改变，但是全局常量需要声明出来，局部常量则无需多加处理
        //全局和局部常量加入符号表即可，后续直接从符号表中取出具体的值进行使用
        String variableName = ident.name(); // 变量名
        boolean isGlobal = Visitor.isGlobal(); // 是否为全局变量
        boolean isArray = (constExp != null); // 是否为数组

        if (!isArray) {
            constInitVal.visit();
            if (isGlobal) { // 全局普通变量
                GlobalVar globalVar = new GlobalVar(variableName, (Constant) Visitor.upValue, true);
                Visitor.model.addGlobalValue(globalVar);
            }
            System.out.println("全局变量：" + variableName);
            Visitor.curTable.addSymbol(variableName, Visitor.upValue);
        } else { // 数组变量
            constExp.visit(); // 处理数组维度
            Visitor.ArraySize = Visitor.upConstValue;
            ArrayType arrayType = new ArrayType(Visitor.ValueType, Visitor.ArraySize); // 构建数组类型

            if (isGlobal) { // 全局数组
                constInitVal.visit(); // 处理数组的初始值
                GlobalVar globalVar = new GlobalVar(variableName, (Constant) Visitor.upValue, true);
                Visitor.model.addGlobalValue(globalVar);
                Visitor.curTable.addSymbol(variableName, globalVar);
            } else { // 局部数组
                alloca alloca = alloca(arrayType); // 分配空间
                Visitor.curTable.addSymbol(variableName, alloca);
                constInitVal.visit(); // 处理数组的初始值
                store(Visitor.upValue, alloca); // 存储数组初始值
            }
        }
    }
}//常量定义ConstDef → Ident [ '[' ConstExp ']' ] '=' ConstInitVal

