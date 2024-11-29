package frontend.node.stmt;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
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

    public void visit() {
        br(Visitor.breakToBlocks.peek());
        enterNewBlock(new BasicBlock("Block_break" + Function.breakNum++));
    }
}
