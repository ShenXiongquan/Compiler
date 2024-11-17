package frontend.node;

import frontend.tool.myWriter;

public class Cond extends node{
    public  LOrExp lOrExp;
    public void print(){
        lOrExp.print();
        myWriter.writeNonTerminal("Cond");
    }
    @Override
    public void visit() {

    }
}//条件表达式
