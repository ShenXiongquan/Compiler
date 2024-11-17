package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class FuncType extends node{
    public token returnType;

    public void print() {
        returnType.print();
        myWriter.writeNonTerminal("FuncType");
    }
    @Override
    public void visit() {

    }
}//函数返回值类型
