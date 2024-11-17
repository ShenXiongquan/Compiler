package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class Number extends node{
    public token intConst;

    public void print() {
        intConst.print();
        myWriter.writeNonTerminal("Number");
    }
    @Override
    public void visit() {

    }
}//数值
