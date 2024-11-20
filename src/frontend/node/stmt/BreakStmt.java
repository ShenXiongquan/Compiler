package frontend.node.stmt;

import frontend.Visitor;
import frontend.ir.instructions.ControlFlowInstructions.br;
import frontend.token.token;
import frontend.tool.myWriter;

public class BreakStmt extends Stmt {
    public token breakToken;
    public token semicn;

    @Override
    public void print() {
        breakToken.print();
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("Stmt");
    }

    @Override
    public void visit() {
//        br br=new br();
//        Visitor.curBlock.addInstruction(br);
    }
}
