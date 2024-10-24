package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class ForStmt {
    public LVal lVal;      // 左值
    public Exp exp;        // 表达式
    public token assign;

    public void visit(){
        lVal.visit();
        assign.visit();
        exp.visit();
        myWriter.writeNonTerminal("ForStmt");
    }
}//for循环语句
