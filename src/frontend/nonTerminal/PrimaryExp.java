package frontend.nonTerminal;

import frontend.token.token;
import frontend.tool.myWriter;

public class PrimaryExp {
    public token lparent;

    public Exp exp;

    public token rparent;
    public LVal lVal;
    public Number number;
    public Character character;

    public void visit(){
        if(lparent!=null){
            lparent.visit();
            exp.visit();
            if(rparent!=null)rparent.visit();
        } else if (lVal!=null) {
            lVal.visit();
        }else if (number!=null){
            number.visit();
        }else {
            character.visit();
        }

        myWriter.writeNonTerminal("PrimaryExp");
    }
}//基本表达式 PrimaryExp → '(' Exp ')' | LVal | Number | Character
