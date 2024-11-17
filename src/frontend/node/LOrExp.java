package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class LOrExp extends node{
    public LAndExp lAndExp;
    public token or;
    public LOrExp lOrExp;

    public void print(){
        if(lOrExp!=null){
            lOrExp.print();
            or.print();
        }
        lAndExp.print();
        myWriter.writeNonTerminal("LOrExp");
    }
    @Override
    public void visit() {

    }
}//逻辑或表达式
