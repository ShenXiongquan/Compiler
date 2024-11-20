package frontend.node.blockItem;

import frontend.node.stmt.Stmt;

public class StmtBlockItem extends BlockItem {
    public Stmt stmt;

    @Override
    public void print() {
        stmt.print();
    }

    @Override
    public void visit() {
        stmt.visit();
    }
}
