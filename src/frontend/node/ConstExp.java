package frontend.node;

import frontend.Visitor;
import frontend.ir.constants.ConstInt;
import frontend.ir.type.IntegerType;
import frontend.tool.myWriter;

public class ConstExp extends node{
    public AddExp addExp;

    public void print(){
        addExp.print();
        myWriter.writeNonTerminal("ConstExp");
    }

    @Override
    public void visit() {
        Visitor.calAble=true;
        addExp.visit();
        Visitor.calAble=false;
    }
}//常量表达式
