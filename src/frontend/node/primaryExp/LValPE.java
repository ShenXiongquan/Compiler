package frontend.node.primaryExp;

import frontend.node.LVal;
import frontend.tool.myWriter;

public class LValPE extends PrimaryExp{
    public LVal lVal;

    @Override
    public void visit() {
        lVal.visit();
        myWriter.writeNonTerminal("PrimaryExp");
    }
}
