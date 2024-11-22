package frontend.node.decl;


public class VarDecl extends Decl{
    public frontend.node.VarDecl varDecl;

    @Override
    public void print() {
        varDecl.print();
    }

    @Override
    public void visit() {
        varDecl.visit();
    }
}
