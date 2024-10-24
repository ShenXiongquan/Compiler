package frontend.node.decl;

import frontend.node.ConstDecl;

public class CONSTDecl extends Decl{
    public ConstDecl constDecl;

    @Override
    public void visit() {
        constDecl.visit();
    }
}
