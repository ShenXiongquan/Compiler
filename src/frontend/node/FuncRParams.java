package frontend.node;

import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class FuncRParams {
    public final List<Exp> exps=new ArrayList<>();
    public List<token> comma=new ArrayList<>();

    public void visit(){
        exps.get(0).visit();
        for(int i=1;i<exps.size();i++){
            comma.get(i-1).visit();
            exps.get(i).visit();
        }
        myWriter.writeNonTerminal("FuncRParams");
    }

}//函数实参表
