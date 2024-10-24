package frontend.node.primaryExp;

import frontend.node.Number;
import frontend.tool.myWriter;

public class NumberPE extends PrimaryExp{
    public Number number;

    @Override
    public void visit() {
        number.visit();
        myWriter.writeNonTerminal("PrimaryExp");
    }
}
