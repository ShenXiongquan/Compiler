package frontend.nonTerminal;

import frontend.token.token;
import frontend.tool.myWriter;

public class LAndExp {
    public EqExp eqExp;
    public token and;
    public LAndExp lAndExp;

    public void visit(){
        if(lAndExp!=null){
            lAndExp.visit();
            and.visit();
        }
        eqExp.visit();
        myWriter.writeNonTerminal("LAndExp");
    }
}//逻辑与表达式
