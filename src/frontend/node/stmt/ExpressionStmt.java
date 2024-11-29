package frontend.node.stmt;

import frontend.node.Exp;
import frontend.token.token;

public class ExpressionStmt extends Stmt {
    public Exp optionalExp;

    public token semicn;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        if (optionalExp != null) sb.append(optionalExp.print());
        if (semicn != null) sb.append(semicn.print());
        sb.append("<Stmt>\n");
        return sb.toString();
    }


    public void visit() {
        if (optionalExp != null) optionalExp.visit();
    }
}
