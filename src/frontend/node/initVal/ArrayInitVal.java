package frontend.node.initVal;

import frontend.node.Exp;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class ArrayInitVal extends InitVal{
    public token lbrace;
    public final List<Exp> exps=new ArrayList<>();
    public token comma;
    public token rbrace;

    @Override
    public void print() {
        lbrace.print();
        if(!exps.isEmpty()){
            exps.get(0).print();
            for(int i=1;i<exps.size();i++){
                comma.print();
                exps.get(i).print();
            }
        }
        rbrace.print();
        myWriter.writeNonTerminal("InitVal");
    }

    @Override
    public void visit() {

    }
}
