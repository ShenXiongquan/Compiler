package frontend.node.stmt;

import frontend.node.Exp;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class PrintfStmt extends Stmt {
    public token printf;
    public token stringConst;
    public final List<Exp> exps=new ArrayList<>();

    public token comma;

    public token lparent;
    public token rparent;
    public token semicn;

    @Override
    public void visit() {
        printf.visit();
        lparent.visit();
        stringConst.visit();
        if (!exps.isEmpty()) for (Exp exp : exps) {
            comma.visit();
            exp.visit();
        }
        if (rparent != null) rparent.visit();
        if (semicn != null) semicn.visit();
        myWriter.writeNonTerminal("Stmt");
    }
}
