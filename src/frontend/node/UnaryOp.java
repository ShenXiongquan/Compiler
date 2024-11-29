package frontend.node;

import frontend.token.token;

public class UnaryOp extends node {
    public token op;

    public String print() {
        return op.print() +
                "<UnaryOp>\n";
    }

}//单目运算符
