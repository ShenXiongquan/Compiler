package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class LAndExp extends node{
    public EqExp eqExp;
    public token and;
    public LAndExp lAndExp;

    public void print() {
        if (lAndExp != null) {
            lAndExp.print();
            and.print();
        }
        eqExp.print();
        myWriter.writeNonTerminal("LAndExp");
    }
    @Override
    public void visit() {

    }
}//逻辑与表达式
