package frontend.node.initVal;

import frontend.node.Exp;
import frontend.tool.myWriter;

public class ExpIV extends InitVal{
    public Exp exp;

    @Override
    public void visit() {
        exp.visit();
        myWriter.writeNonTerminal("InitVal");
    }
}
