package frontend.node;

import frontend.Visitor;
import frontend.ir.constants.ConstInt;
import frontend.tool.myWriter;

public class Exp extends node{
    public AddExp addExp;
    public void print(){
        addExp.print();
        myWriter.writeNonTerminal("Exp");
    }
    @Override
    public void visit() {
        addExp.visit();
    }
}//表达式
