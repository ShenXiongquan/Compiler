package frontend.node.constInitVal;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.type.IntegerType;
import frontend.node.ConstExp;
import frontend.tool.myWriter;

public class ExpConstInitVal extends ConstInitVal{
    public ConstExp constExp;

    @Override
    public void print() {
        constExp.print();
        myWriter.writeNonTerminal("ConstInitVal");
    }

    public void visit() {
         constExp.visit();
         Visitor.upValue=new ConstInt((IntegerType) Visitor.ValueType,Visitor.upConstValue);//全局和局部常量
    }
}
