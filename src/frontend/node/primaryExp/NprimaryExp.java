package frontend.node.primaryExp;

import frontend.node.Number;
import frontend.tool.myWriter;

public class NprimaryExp extends PrimaryExp{
    public Number number;

    @Override
    public void print() {
        number.print();
        myWriter.writeNonTerminal("PrimaryExp");
    }

    public void visit() {
        number.visit();
    }
}
