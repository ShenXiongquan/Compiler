package frontend.node.stmt;

import frontend.node.Block;
import frontend.tool.myWriter;

public class BlockStmt extends Stmt {
    public Block block;

    @Override
    public void print() {
        block.print();
        myWriter.writeNonTerminal("Stmt");
    }
    @Override
    public void visit() {
        block.visit();
    }
}
