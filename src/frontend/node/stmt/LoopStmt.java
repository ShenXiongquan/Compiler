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
    public void print() {
        forToken.print();
        lparent.print();
        if (initForStmt != null) initForStmt.print();
        semicn1.print();
        if (forCondition != null) forCondition.print();
        semicn2.print();
        if (updateForStmt != null) updateForStmt.print();
        rparent.print();
        forBody.print();
        myWriter.writeNonTerminal("Stmt");
    }
    @Override
    public void visit() {

    }
}
