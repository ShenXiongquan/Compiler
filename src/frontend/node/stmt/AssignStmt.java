package frontend.node.stmt;

import frontend.node.Exp;
import frontend.node.LVal;
import frontend.token.token;
import frontend.tool.myWriter;

public class AssignStmt extends Stmt {
    public LVal lVal;
    public Exp exp;
    public token assign;
    public token semicn;

    @Override
    public void print() {
        lVal.print();
        assign.print();
        exp.print();
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("Stmt");
    }

    @Override
    public void visit() {

    }
}
