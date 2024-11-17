package frontend.node.stmt;

import frontend.node.Exp;
import frontend.token.token;
import frontend.tool.myWriter;

public class ReturnStmt extends Stmt {
    public token returnToken;
    public Exp returnExp;

    public token semicn;

    @Override
    public void print() {
        returnToken.print();
        if (returnExp != null) returnExp.print();
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("Stmt");
    }
    @Override
    public void visit() {

    }
}
