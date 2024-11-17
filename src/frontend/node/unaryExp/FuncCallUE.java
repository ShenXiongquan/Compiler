package frontend.node.unaryExp;

import frontend.node.FuncRParams;
import frontend.token.token;
import frontend.tool.myWriter;

public class FuncCallUE extends UnaryExp{
    public token lparent;
    public token ident;
    public token rparent;
    public FuncRParams funcRParams;


    @Override
    public void print() {
        ident.print();
        lparent.print();
        if(funcRParams!=null)funcRParams.print();
        if(rparent!=null)rparent.print();
        myWriter.writeNonTerminal("UnaryExp");
    }

    @Override
    public void visit() {

    }
}
