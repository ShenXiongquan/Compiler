package frontend.node;

import frontend.tool.myWriter;

public class Exp extends node{
    public AddExp addExp;
    public void print(){
        addExp.print();
        myWriter.writeNonTerminal("Exp");
    }
    public void visit() {
        addExp.visit();
    }
}//表达式
