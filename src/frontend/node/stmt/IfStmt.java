package frontend.node.stmt;

import frontend.node.Cond;

import frontend.token.token;
import frontend.tool.myWriter;

public class IfStmt extends Stmt {
    public token ifToken;
    public token lparent;
    public Cond cond;

    public token rparent;
    public Stmt thenStmt;

    public token elseToken;
    public Stmt elseStmt;  // 这可能为null，如果没有else分支的话

    @Override
    public void print() {
        ifToken.print();
        lparent.print();
        cond.print();
        if (rparent != null) rparent.print();
        thenStmt.print();
        if (elseToken != null) {
            elseToken.print();
            elseStmt.print();
        }
        myWriter.writeNonTerminal("Stmt");
    }
    @Override
    public void visit() {

    }
}