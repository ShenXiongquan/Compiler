package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class LOrExp {
    public LAndExp lAndExp;
    public token or;
    public LOrExp lOrExp;

    public void visit(){
        if(lOrExp!=null){
            lOrExp.visit();
            or.visit();
        }
        lAndExp.visit();
        myWriter.writeNonTerminal("LOrExp");
    }
}//逻辑或表达式
