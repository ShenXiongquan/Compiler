package frontend.nonTerminal;

import frontend.token.token;

public class BType {
    public token type;

    public void visit(){
        type.visit();
    }
}//基本类型
