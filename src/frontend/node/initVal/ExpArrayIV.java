package frontend.node.initVal;

import frontend.node.Exp;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class ExpArrayIV extends InitVal{
    public token lbrace;
    public final List<Exp> exps=new ArrayList<>();
    public token comma;
    public token rbrace;

    @Override
    public void visit() {
        lbrace.visit();
        if(!exps.isEmpty()){
            exps.get(0).visit();
            for(int i=1;i<exps.size();i++){
                comma.visit();
                exps.get(i).visit();
            }
        }
        rbrace.visit();
        myWriter.writeNonTerminal("InitVal");
    }
}
