package frontend.node.blockItem;

import frontend.node.decl.Decl;

public class DBlockItem extends BlockItem {
    public Decl decl;

    @Override
    public String print() {
        return decl.print();
    }

    public void visit() {
        decl.visit();
    }
}
