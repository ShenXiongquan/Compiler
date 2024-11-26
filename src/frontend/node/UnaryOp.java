package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class UnaryOp extends node {
    public token op;

    public void print() {
        op.print();
        myWriter.writeNonTerminal("UnaryOp");
    }
}//单目运算符
