package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class ForStmt extends node{
    public LVal lVal;      // 左值
    public Exp exp;        // 表达式
    public token assign;

    public void print(){
        lVal.print();
        assign.print();
        exp.print();
        myWriter.writeNonTerminal("ForStmt");
    }
    @Override
    public void visit() {

    }
}//for循环语句
