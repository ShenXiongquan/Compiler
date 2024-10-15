package frontend.nonTerminal;

import frontend.tool.myWriter;

public class Exp {
    public AddExp addExp;

    public void visit(){
        addExp.visit();
        myWriter.writeNonTerminal("Exp");
    }
}//表达式
