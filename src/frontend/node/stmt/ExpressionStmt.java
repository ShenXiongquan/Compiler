package frontend.node.stmt;

import frontend.node.Exp;

import frontend.token.token;
import frontend.tool.myWriter;

public class ExpressionStmt extends Stmt {
    public Exp optionalExp;

    public token semicn;

    @Override
    public void print() {
        if (optionalExp != null) optionalExp.print();
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("Stmt");
    }

    @Override
    public void visit() {
        if(optionalExp!=null)optionalExp.visit();
    }
}
