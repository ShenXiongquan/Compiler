package frontend.node.decl;


import frontend.node.node;

public abstract class Decl extends node {

    public abstract String print();

    public abstract void visit();
}//常量和变量声明
