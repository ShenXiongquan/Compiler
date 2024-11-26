package frontend.node.stmt;

import frontend.llvm_ir.Visitor;
import frontend.node.Block;
import frontend.tool.myWriter;

public class BlockStmt extends Stmt {
    public Block block;

    @Override
    public void print() {

        block.print();

        myWriter.writeNonTerminal("Stmt");
    }
    public void visit() {
        Visitor.pushScope();
        block.visit();
        Visitor.popScope();
    }
}
