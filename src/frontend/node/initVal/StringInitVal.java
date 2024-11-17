package frontend.node.initVal;

import frontend.token.token;
import frontend.tool.myWriter;

public class StringInitVal extends InitVal{
    public token stringConst;

    @Override
    public void print() {
        stringConst.print();
        myWriter.writeNonTerminal("InitVal");
    }

    @Override
    public void visit() {

    }
}
