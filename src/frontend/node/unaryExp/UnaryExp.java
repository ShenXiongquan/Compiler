package frontend.node.unaryExp;


import frontend.node.node;

public abstract class UnaryExp extends node {
    public abstract String print();

    public abstract void visit();

}  //UnaryExp → PrimaryExp | Ident '(' [FuncRParams] ')' | UnaryOp UnaryExp
