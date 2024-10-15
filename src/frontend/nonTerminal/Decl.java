package frontend.nonTerminal;


public class Decl {
    public ConstDecl constDecl;
    public VarDecl varDecl;

    public void visit() {
        if (constDecl != null) constDecl.visit();
        else varDecl.visit();
    }
}//常量和变量声明
