package frontend.node;

import frontend.node.unaryExp.UnaryExp;
import frontend.token.token;
import frontend.tool.myWriter;

public class MulExp {

    public MulExp mulExp;
    public token op;
    public UnaryExp unaryExp;

    public void visit(){
        if(mulExp!=null){
            mulExp.visit();
            op.visit();
        }
        unaryExp.visit();
        myWriter.writeNonTerminal("MulExp");
    }
}//乘除模表达式
