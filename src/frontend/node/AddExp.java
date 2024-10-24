package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class AddExp {
    public AddExp addExp;
    public MulExp mulExp;
    public token op;

    public void visit(){
        if(addExp!=null){
            addExp.visit();
            op.visit();
        }
        mulExp.visit();
        myWriter.writeNonTerminal("AddExp");
    }
}//加减表达式
