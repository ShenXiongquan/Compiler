package frontend.node;


import frontend.node.initVal.InitVal;
import frontend.token.token;
import frontend.tool.myWriter;

public class VarDef {
    public token ident;
    public token lbrack;
    public ConstExp constExp;
    public token rbrack;
    public token assign;
    public InitVal initVal;

    public void visit(){
        ident.visit();
        if(lbrack!=null){
            lbrack.visit();
            constExp.visit();
            if(rbrack!=null)rbrack.visit();
        }
        if(assign!=null){
            assign.visit();
            initVal.visit();
        }
        myWriter.writeNonTerminal("VarDef");
    }
}//变量定义 VarDef → Ident [ '[' ConstExp ']' ] | Ident [ '[' ConstExp ']' ] '='InitVal
