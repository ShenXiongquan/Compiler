package frontend.nonTerminal.stmt;

import frontend.nonTerminal.Exp;
import frontend.nonTerminal.LVal;
import frontend.token.token;
import frontend.tool.myWriter;

public class AssignStmt extends Stmt {
    public LVal lVal;
    public Exp exp;
    public token assign;
    public token semicn;

    @Override
    public void visit() {
        lVal.visit();
        assign.visit();
        exp.visit();
        if (semicn != null) semicn.visit();
        myWriter.writeNonTerminal("Stmt");
    }
}
