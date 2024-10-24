package frontend.node.decl;


import frontend.node.VarDecl;

public class VARDecl extends Decl{
    public VarDecl varDecl;

    @Override
    public void visit() {
        varDecl.visit();
    }
}
