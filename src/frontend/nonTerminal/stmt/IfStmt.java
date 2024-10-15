package frontend.nonTerminal.stmt;

import frontend.nonTerminal.Cond;

import frontend.token.token;
import frontend.tool.myWriter;

public class IfStmt extends Stmt {
    public token ifToken;
    public token lparent;
    public Cond condition;

    public token rparent;
    public Stmt thenStmt;

    public token elseToken;
    public Stmt elseStmt;  // 这可能为null，如果没有else分支的话

    @Override
    public void visit() {
        ifToken.visit();
        lparent.visit();
        condition.visit();
        if (rparent != null) rparent.visit();
        thenStmt.visit();
        if (elseToken != null) {
            elseToken.visit();
            elseStmt.visit();
        }
        myWriter.writeNonTerminal("Stmt");
    }

}