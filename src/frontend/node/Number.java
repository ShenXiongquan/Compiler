package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class Number {
    public token intConst;

    public void visit() {
        intConst.visit();
        myWriter.writeNonTerminal("Number");
    }
}//数值
