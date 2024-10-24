package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class LVal {
    public token ident;
    public token lbrack;

    public Exp exp;
    public token rbrack;

    public void visit(){
        ident.visit();
        if(lbrack!=null){
            lbrack.visit();
            exp.visit();
            if(rbrack!=null)rbrack.visit();
        }
        myWriter.writeNonTerminal("LVal");
    }
}//左值表达式 LVal → Ident ['[' Exp ']']
