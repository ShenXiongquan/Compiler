package frontend.node.blockItem;

import frontend.node.decl.Decl;

public class DeclBlockItem extends BlockItem{
    public Decl decl;

    @Override
    public void print() {
        decl.print();
    }

    @Override
    public void visit() {
        decl.visit();
    }
}
