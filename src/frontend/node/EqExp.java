package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class EqExp extends node{
    public RelExp relExp;
    public token op;
    public EqExp eqExp;

    public void print(){
        if(eqExp!=null){
            eqExp.print();
            op.print();
        }
        relExp.print();
        myWriter.writeNonTerminal("EqExp");
    }

    @Override
    public void visit() {

    }
}//相等性表达式
