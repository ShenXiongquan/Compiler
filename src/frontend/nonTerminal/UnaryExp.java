package frontend.nonTerminal;

import frontend.token.token;
import frontend.tool.myWriter;

public class UnaryExp {

    public PrimaryExp primaryExp;
    public token ident,lparent,rparent;
    public FuncRParams funcRParams;
    public UnaryOp unaryOp;
    public UnaryExp unaryExp;

    public void visit(){
        if(primaryExp!=null){
            primaryExp.visit();
        } else if (ident!=null) {
            ident.visit();
            lparent.visit();
            if(funcRParams!=null)funcRParams.visit();
            if(rparent!=null)rparent.visit();
        }else {
            unaryOp.visit();
            unaryExp.visit();
        }
        myWriter.writeNonTerminal("UnaryExp");
    }
}//一元表达式 UnaryExp → PrimaryExp | Ident '(' [FuncRParams] ')' | UnaryOp UnaryExp
