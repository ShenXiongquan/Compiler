package frontend.node.stmt;


import frontend.node.node;

public abstract class Stmt extends node {
    public abstract void print();
    @Override
    public abstract void visit();
}


