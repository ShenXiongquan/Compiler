package frontend.node.stmt;

import frontend.Visitor;
import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.instructions.ControlFlowInstructions.br;
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
       br br=new br(Visitor.breakToBlocks.peek());
       Visitor.curBlock.addInstruction(br);
       Visitor.curBlock=new BasicBlock("Block_break"+ Function.breakNum++);
       Visitor.curFunc.addBasicBlock(Visitor.curBlock);
    }
}
