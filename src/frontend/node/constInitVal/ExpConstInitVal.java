package frontend.node.constInitVal;

import frontend.Visitor;
import frontend.ir.constants.ConstInt;
import frontend.ir.type.IntegerType;
import frontend.node.ConstExp;
import frontend.tool.myWriter;

public class ExpConstInitVal extends ConstInitVal{
    public ConstExp constExp;

    @Override
    public void print() {
        constExp.print();
        myWriter.writeNonTerminal("ConstInitVal");
    }

    @Override
    public void visit() {
         constExp.visit();
         Visitor.upValue=new ConstInt((IntegerType) Visitor.ValueType,Visitor.upConstValue);
    }
}
