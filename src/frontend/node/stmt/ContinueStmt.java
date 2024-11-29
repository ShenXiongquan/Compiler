package frontend.node.stmt;


import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.Visitor;
import frontend.token.token;

public class ContinueStmt extends Stmt {
    public token continueToken;
    public token semicn;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(continueToken.print());
        if (semicn != null) sb.append(semicn.print());
        sb.append("<Stmt>\n");
        return sb.toString();
    }

    public void visit() {
        br(Visitor.continueToBlocks.peek());
        enterNewBlock(new BasicBlock("Block_continue" + Function.continueNum++));

    }
}
