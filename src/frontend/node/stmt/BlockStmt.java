package frontend.node.stmt;

import frontend.Visitor;
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
        Visitor.curTable=Visitor.curTable.pushScope();
        block.visit();
        Visitor.curTable=Visitor.curTable.popScope();
    }
}
