package frontend.node.decl;


import frontend.node.VarDecl;

public class VDecl extends Decl{
    public VarDecl varDecl;

    @Override
    public void print() {
        varDecl.print();
    }

    public void visit() {
        varDecl.visit();
    }
}
