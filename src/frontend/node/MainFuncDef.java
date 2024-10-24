package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class MainFuncDef {
    public token intToken;
    public token main;

    public token lparent;

    public token rparent;
    public Block block;

    public void visit(){
        intToken.visit();
        main.visit();
        lparent.visit();
        if(rparent!=null)rparent.visit();
        block.visit();
        myWriter.writeNonTerminal("MainFuncDef");
    }
}//主函数声明MainFuncDef → 'int' 'main' '(' ')' Block
