package frontend.nonTerminal;

import frontend.token.token;
import frontend.tool.myWriter;

public class EqExp {
    public RelExp relExp;
    public token op;
    public EqExp eqExp;

    public void visit(){
        if(eqExp!=null){
            eqExp.visit();
            op.visit();
        }
        relExp.visit();
        myWriter.writeNonTerminal("EqExp");
    }
}//相等性表达式
