package frontend.nonTerminal;

import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class InitVal {
    public token lbrace;
    public final List<Exp> exps=new ArrayList<>();
    public token comma;
    public token rbrace;
    public token stringConst;

    public void visit(){
        if(stringConst!=null){
            stringConst.visit();
        }else if(lbrace!=null){
            lbrace.visit();
            if(!exps.isEmpty()){
                exps.get(0).visit();
                for(int i=1;i<exps.size();i++){
                    comma.visit();
                    exps.get(i).visit();
                }
            }
            rbrace.visit();
        }else {
            exps.get(0).visit();
        }
        myWriter.writeNonTerminal("InitVal");
    }
}//变量初值
