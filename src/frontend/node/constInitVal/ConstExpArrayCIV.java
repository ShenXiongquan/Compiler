package frontend.node.constInitVal;

import frontend.node.ConstExp;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class ConstExpArrayCIV extends ConstInitVal{

    public token lbrace;
    public final List<ConstExp> constExps=new ArrayList<>();

    public token comma;
    public token rbrace;

    @Override
    public void visit() {
            lbrace.visit();
            if(!constExps.isEmpty()){
                constExps.get(0).visit();
                for(int i=1;i<constExps.size();i++){
                    comma.visit();
                    constExps.get(i).visit();
                }
            }
            rbrace.visit();
        myWriter.writeNonTerminal("ConstInitVal");
    }
}
