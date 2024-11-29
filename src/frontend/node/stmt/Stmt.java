package frontend.node.stmt;


import frontend.node.node;

public abstract class Stmt extends node {
    public abstract String print();

    public abstract void visit();
}


