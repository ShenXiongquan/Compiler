package frontend.node;

import frontend.tool.myWriter;

public class Cond {
    public  LOrExp lOrExp;
    public void visit(){
        lOrExp.visit();
        myWriter.writeNonTerminal("Cond");
    }
}//条件表达式
