package frontend.node.primaryExp;

import frontend.node.Exp;
import frontend.token.token;
import frontend.tool.myWriter;

public class ExpPE extends PrimaryExp{
    public token lparent;

    public Exp exp;

    public token rparent;

    @Override
    public void visit() {
        lparent.visit();
        exp.visit();
        if(rparent!=null)rparent.visit();
        myWriter.writeNonTerminal("PrimaryExp");
    }
}
