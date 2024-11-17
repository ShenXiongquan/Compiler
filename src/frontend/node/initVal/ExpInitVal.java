package frontend.node.initVal;

import frontend.node.Exp;
import frontend.tool.myWriter;

public class ExpInitVal extends InitVal{
    public Exp exp;

    @Override
    public void print() {
        exp.print();
        myWriter.writeNonTerminal("InitVal");
    }

    @Override
    public void visit() {

    }
}
