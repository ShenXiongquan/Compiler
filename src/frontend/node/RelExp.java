package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class RelExp {

    public AddExp addExp;
    public RelExp relExp;
    public token relOp;

    public void visit(){
        if(relExp!=null){
            relExp.visit();
            relOp.visit();
        }
        addExp.visit();
        myWriter.writeNonTerminal("RelExp");
    }

}//关系表达式
