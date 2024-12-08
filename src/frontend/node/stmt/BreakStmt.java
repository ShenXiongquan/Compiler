package frontend.node.stmt;

import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Visitor;
import frontend.token.token;

public class BreakStmt extends Stmt {
    public token breakToken;
    public token semicn;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(breakToken.print());
        if (semicn != null) sb.append(semicn.print());
        sb.append("<Stmt>\n");
        return sb.toString();
    }


    public void visit() {
        br(Visitor.breakToBlocks.peek());
        enterNewBlock(new BasicBlock());
    }
}
