package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class FuncFParams extends node{
    public final List<FuncFParam> funcFParams=new ArrayList<>();
    public List<token> comma=new ArrayList<>();

    public void print(){
        funcFParams.get(0).print();
        for(int i=1;i<funcFParams.size();i++){
            comma.get(i-1).print();
            funcFParams.get(i).print();
        }
        myWriter.writeNonTerminal("FuncFParams");
    }

    @Override
    public void visit() {

    }
}//函数形参表
