package frontend.node.primaryExp;

import frontend.node.Exp;
import frontend.token.token;
import frontend.tool.myWriter;

public class ExpPE extends PrimaryExp {
    public token lparent;

    public Exp exp;

    public token rparent;

    @Override
    public void print() {
        lparent.print();
        exp.print();
        if (rparent != null) rparent.print();
        myWriter.writeNonTerminal("PrimaryExp");
    }

    public void visit() {
        exp.visit();
    }
}
