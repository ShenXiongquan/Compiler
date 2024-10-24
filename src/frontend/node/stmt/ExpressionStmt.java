package frontend.node.stmt;

import frontend.node.Exp;

import frontend.token.token;
import frontend.tool.myWriter;

public class ExpressionStmt extends Stmt {
    public Exp optionalExp;

    public token semicn;

    @Override
    public void visit() {
        if (optionalExp != null) optionalExp.visit();
        if (semicn != null) semicn.visit();
        myWriter.writeNonTerminal("Stmt");
    }
}
