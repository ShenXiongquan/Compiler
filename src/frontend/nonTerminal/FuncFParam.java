package frontend.nonTerminal;

import frontend.token.token;
import frontend.tool.myWriter;

public class FuncFParam {
    public BType bType;
    public token ident;
    public token lbrack;
    public token rbrack;

    public void visit(){
        bType.visit();
        ident.visit();
        if(lbrack!=null){
            lbrack.visit();
            if(rbrack!=null)rbrack.visit();
        }
        myWriter.writeNonTerminal("FuncFParam");
    }
}//函数形参FuncFParam → BType Ident ['[' ']']
