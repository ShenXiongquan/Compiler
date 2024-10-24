package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class FuncDef {
    public FuncType funcType;
    public token ident;

    public token lparent;
    public FuncFParams funcFParams;

    public token rparent;
    public Block block;

    public void visit(){
        funcType.visit();
        ident.visit();
        lparent.visit();
        if(funcFParams!=null)funcFParams.visit();
        if(rparent!=null)rparent.visit();
        block.visit();
        myWriter.writeNonTerminal("FuncDef");
    }
}//函数声明FuncDef → FuncType Ident '(' [FuncFParams] ')' Block
