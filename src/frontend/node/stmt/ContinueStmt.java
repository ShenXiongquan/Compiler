package frontend.node.stmt;


import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.token.token;
import frontend.tool.myWriter;

public class ContinueStmt extends Stmt {
    public token continueToken;
    public token semicn;

    @Override
    public void print() {
        continueToken.print();
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("Stmt");
    }
    public void visit() {
        br(Visitor.continueToBlocks.peek());
        enterNewBlock(new BasicBlock("Block_continue"+ Function.continueNum++));

    }
}
