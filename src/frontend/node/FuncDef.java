package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class FuncDef extends node{
    public FuncType funcType;
    public token ident;

    public token lparent;
    public FuncFParams funcFParams;

    public token rparent;
    public Block block;

    public void print(){
        funcType.print();
        ident.print();
        lparent.print();
        if(funcFParams!=null)funcFParams.print();
        if(rparent!=null)rparent.print();
        block.print();
        myWriter.writeNonTerminal("FuncDef");
    }
    @Override
    public void visit() {

    }
}//函数声明FuncDef → FuncType Ident '(' [FuncFParams] ')' Block
