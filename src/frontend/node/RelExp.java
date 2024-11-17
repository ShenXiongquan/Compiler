package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class RelExp extends node {

    public AddExp addExp;
    public RelExp relExp;
    public token relOp;

    public void print(){
        if(relExp!=null){
            relExp.print();
            relOp.print();
        }
        addExp.print();
        myWriter.writeNonTerminal("RelExp");
    }
    @Override
    public void visit() {

    }
}//关系表达式
