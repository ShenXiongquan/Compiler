package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class FuncFParams {
    public final List<FuncFParam> funcFParams=new ArrayList<>();
    public List<token> comma=new ArrayList<>();

    public void visit(){
        funcFParams.get(0).visit();
        for(int i=1;i<funcFParams.size();i++){
            comma.get(i-1).visit();
            funcFParams.get(i).visit();
        }
        myWriter.writeNonTerminal("FuncFParams");
    }
}//函数形参表
