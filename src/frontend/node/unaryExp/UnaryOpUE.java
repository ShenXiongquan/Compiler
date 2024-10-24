package frontend.node.unaryExp;

import frontend.node.UnaryOp;
import frontend.tool.myWriter;

public class UnaryOpUE extends UnaryExp{
    public UnaryOp unaryOp;
    public UnaryExp unaryExp;

    @Override
    public void visit() {
        unaryOp.visit();
        unaryExp.visit();
        myWriter.writeNonTerminal("UnaryExp");
    }
}
