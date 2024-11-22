package frontend.node.unaryExp;


import frontend.node.node;

public abstract class UnaryExp extends node {

    //UnaryExp → PrimaryExp | Ident '(' [FuncRParams] ')' | UnaryOp UnaryExp
    public abstract void print();


    public abstract void visit();

}
