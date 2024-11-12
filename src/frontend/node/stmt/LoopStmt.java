package frontend.node.stmt;

import frontend.node.Cond;
import frontend.node.ForStmt;
import frontend.token.token;
import frontend.tool.myWriter;

public class LoopStmt extends Stmt {
    public token forToken;
    public token lparent;

    public ForStmt initForStmt;  // 这通常是一个赋值语句或者声明语句
    public token semicn1;
    public Cond forCondition;
    public token semicn2;
    public ForStmt updateForStmt;

    public token rparent;

    public Stmt forBody;

    @Override
    public void visit() {
        forToken.visit();
        lparent.visit();
        if (initForStmt != null) initForStmt.visit();
        semicn1.visit();
        if (forCondition != null) forCondition.visit();
        semicn2.visit();
        if (updateForStmt != null) updateForStmt.visit();
        rparent.visit();
        forBody.visit();
        myWriter.writeNonTerminal("Stmt");
    }
}
