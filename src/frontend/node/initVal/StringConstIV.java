package frontend.node.initVal;

import frontend.token.token;
import frontend.tool.myWriter;

public class StringConstIV extends InitVal{
    public token stringConst;

    @Override
    public void visit() {
        stringConst.visit();
        myWriter.writeNonTerminal("InitVal");
    }
}
