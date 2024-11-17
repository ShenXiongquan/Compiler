package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class MainFuncDef extends node {
    public token intToken;
    public token main;

    public token lparent;

    public token rparent;
    public Block block;

    public void print() {
        intToken.print();
        main.print();
        lparent.print();
        if (rparent != null) rparent.print();
        block.print();
        myWriter.writeNonTerminal("MainFuncDef");
    }
    @Override
    public void visit() {

    }
}//主函数声明MainFuncDef → 'int' 'main' '(' ')' Block
