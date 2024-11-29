package frontend.node.blockItem;

import frontend.node.stmt.Stmt;

public class SBlockItem extends BlockItem {
    public Stmt stmt;

    @Override
    public String print() {
        return stmt.print();
    }

    public void visit() {
        stmt.visit();
    }
}
