package frontend.node.decl;

public class ConstDecl extends Decl{
    public frontend.node.ConstDecl constDecl;

    @Override
    public void print() {
        constDecl.print();
    }

    @Override
    public void visit() {
        constDecl.visit();
    }
}
