package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

public class FuncType {
    public token returnType;

    public void visit(){
        returnType.visit();
        myWriter.writeNonTerminal("FuncType");
    }
}//函数返回值类型
