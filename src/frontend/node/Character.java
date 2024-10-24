package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class Character {
    public token charConst;

    public void visit(){
        charConst.visit();
        myWriter.writeNonTerminal("Character");
    }
}//字符
