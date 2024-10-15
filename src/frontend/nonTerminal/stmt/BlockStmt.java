package frontend.nonTerminal.stmt;

import frontend.nonTerminal.Block;
import frontend.tool.myWriter;

public class BlockStmt extends Stmt {
    public Block block;

    @Override
    public void visit() {
        block.visit();
        myWriter.writeNonTerminal("Stmt");
    }
}
