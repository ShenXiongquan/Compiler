package frontend.nonTerminal;

import frontend.tool.myWriter;

public class ConstExp {
    public AddExp addExp;

    public void visit(){
        addExp.visit();
        myWriter.writeNonTerminal("ConstExp");
    }
}//常量表达式
