package frontend.node.primaryExp;


import frontend.node.node;

public abstract class PrimaryExp extends node {

    public abstract void print();

    public abstract void visit();
}//基本表达式 PrimaryExp → '(' Exp ')' | LVal | Number | Character
