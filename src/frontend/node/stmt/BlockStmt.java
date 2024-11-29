package frontend.node.stmt;

import frontend.llvm_ir.Visitor;
import frontend.node.Block;

public class BlockStmt extends Stmt {
    public Block block;

    @Override
    public String print() {
        return block.print() +
                "<Stmt>\n";
    }

    public void visit() {
        Visitor.pushScope();
        block.visit();
        Visitor.popScope();
    }
}
