package frontend.node.unaryExp;


import frontend.node.primaryExp.PrimaryExp;
import frontend.tool.myWriter;

public class PrimaryExpUE extends UnaryExp{
    public PrimaryExp primaryExp;

    @Override
    public void print() {
        primaryExp.print();
        myWriter.writeNonTerminal("UnaryExp");
    }

    @Override
    public void visit() {
        primaryExp.visit();
    }
}
