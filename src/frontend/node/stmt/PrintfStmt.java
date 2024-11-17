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
    public void print() {
        printf.print();
        lparent.print();
        stringConst.print();
        if (!exps.isEmpty()) for (Exp exp : exps) {
            comma.print();
            exp.print();
        }
        if (rparent != null) rparent.print();
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("Stmt");
    }
    @Override
    public void visit() {

    }
}
