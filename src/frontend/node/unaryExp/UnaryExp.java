package frontend.node.unaryExp;


public abstract class UnaryExp {
    //UnaryExp → PrimaryExp | Ident '(' [FuncRParams] ')' | UnaryOp UnaryExp
    public abstract void visit();

}
