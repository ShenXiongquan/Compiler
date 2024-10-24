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
    public void visit() {
        ident.visit();
        lparent.visit();
        if(funcRParams!=null)funcRParams.visit();
        if(rparent!=null)rparent.visit();
        myWriter.writeNonTerminal("UnaryExp");
    }
}
