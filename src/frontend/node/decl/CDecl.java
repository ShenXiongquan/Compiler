package frontend.node.decl;

import frontend.node.ConstDecl;

public class CDecl extends Decl {
    public ConstDecl constDecl;

    @Override
    public String print() {
        return constDecl.print();
    }

    public void visit() {
        constDecl.visit();
    }
}
