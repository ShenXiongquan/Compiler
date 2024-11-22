package frontend.node.primaryExp;

import frontend.Visitor;
import frontend.node.Number;
import frontend.tool.myWriter;

public class NumberPE extends PrimaryExp{
    public Number number;

    @Override
    public void print() {
        number.print();
        myWriter.writeNonTerminal("PrimaryExp");
    }

    @Override
    public void visit() {
        number.visit();
    }
}
