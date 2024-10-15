package frontend.nonTerminal;

import frontend.token.token;
import frontend.tool.myWriter;

public class ConstDef {
    public token ident;
    public token lbrack;
    public ConstExp constExp;
    public token rbrack;

    public token assign;
    public ConstInitVal constInitVal;

    public void visit(){
        ident.visit();
        if(lbrack!=null){
            lbrack.visit();
            constExp.visit();
            if(rbrack!=null)rbrack.visit();
        }
        assign.visit();
        constInitVal.visit();
        myWriter.writeNonTerminal("ConstDef");
    }
}//常量定义
