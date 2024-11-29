package frontend.node.decl;

import frontend.node.ConstDecl;

public class CDecl extends Decl{
    public ConstDecl constDecl;

    @Override
    public void print() {
        constDecl.print();
    }

    public void visit() {
        constDecl.visit();
    }
}
