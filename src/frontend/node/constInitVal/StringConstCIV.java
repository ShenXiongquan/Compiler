package frontend.node.constInitVal;

import frontend.token.token;
import frontend.tool.myWriter;

public class StringConstCIV extends ConstInitVal{
    public token stringConst;

    @Override
    public void visit() {
        stringConst.visit();
        myWriter.writeNonTerminal("ConstInitVal");
    }
}
