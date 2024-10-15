package frontend.nonTerminal;

import frontend.token.token;
import frontend.tool.myWriter;

public class UnaryOp {
    public token op;

    public void visit(){
        op.visit();
        myWriter.writeNonTerminal("UnaryOp");
    }
}//单目运算符
