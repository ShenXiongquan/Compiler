package frontend.node.stmt;

import frontend.node.LVal;
import frontend.token.token;
import frontend.tool.myWriter;

public class GetIntStmt extends Stmt {
    public LVal lVal;
    public token getint;

    public token assign;
    public token lparent;
    public token rparent;
    public token semicn;

    @Override
    public void print() {
        lVal.print();
        assign.print();
        getint.print();
        lparent.print();
        if (rparent != null) rparent.print();
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("Stmt");
    }
    @Override
    public void visit() {

    }
}
