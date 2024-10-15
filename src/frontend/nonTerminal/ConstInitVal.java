package frontend.nonTerminal;

import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class ConstInitVal {
    public token lbrace;
    public final List<ConstExp> constExps=new ArrayList<>();
    public token comma;
    public token rbrace;
    public token stringConst;

    public void visit(){
        if(stringConst!=null){
            stringConst.visit();
        }else if(lbrace!=null){
            lbrace.visit();
            if(!constExps.isEmpty()){
                constExps.get(0).visit();
                for(int i=1;i<constExps.size();i++){
                    comma.visit();
                    constExps.get(i).visit();
                }
            }
            rbrace.visit();
        }else {
            constExps.get(0).visit();
        }
        myWriter.writeNonTerminal("ConstInitVal");
    }
}//常量初值
