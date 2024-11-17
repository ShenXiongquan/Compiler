package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class FuncFParam extends node{
    public BType bType;
    public token ident;
    public token lbrack;
    public token rbrack;

    public void print(){
        bType.print();
        ident.print();
        if(lbrack!=null){
            lbrack.print();
            if(rbrack!=null)rbrack.print();
        }
        myWriter.writeNonTerminal("FuncFParam");
    }
    @Override
    public void visit() {

    }
}//函数形参FuncFParam → BType Ident ['[' ']']
