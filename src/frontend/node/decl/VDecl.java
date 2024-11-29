package frontend.node.decl;


import frontend.node.VarDecl;

public class VDecl extends Decl {
    public VarDecl varDecl;

    @Override
    public String print() {
        return varDecl.print();
    }

    public void visit() {
        varDecl.visit();
    }
}
