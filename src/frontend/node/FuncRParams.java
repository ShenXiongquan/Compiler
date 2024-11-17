package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class FuncRParams extends node{
    public final List<Exp> exps=new ArrayList<>();
    public List<token> comma=new ArrayList<>();

    public void print(){
        exps.get(0).print();
        for(int i=1;i<exps.size();i++){
            comma.get(i-1).print();
            exps.get(i).print();
        }
        myWriter.writeNonTerminal("FuncRParams");
    }
    @Override
    public void visit() {

    }
}//函数实参表
