package frontend.node.stmt;

import frontend.node.LVal;

import frontend.token.token;
import frontend.tool.myWriter;

public class GetCharStmt extends Stmt {
    public LVal lVal;
    public token getchar;

    public token assign;

    public token lparent;

    public token rparent;

    public token semicn;

    @Override
    public void visit() {
        lVal.visit();
        assign.visit();
        getchar.visit();
        lparent.visit();
        if (rparent != null) rparent.visit();
        if (semicn != null) semicn.visit();
        myWriter.writeNonTerminal("Stmt");
    }
}
