package frontend.nonTerminal;

import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class VarDecl {

    public BType bType;
    public final List<VarDef> varDefs = new ArrayList<>();
    public token comma;

    public token semicn;

    public void visit(){
        bType.visit();
        varDefs.get(0).visit();
        for(int i=1;i<varDefs.size();i++){
            comma.visit();
            varDefs.get(i).visit();
        }
        if(semicn!=null)semicn.visit();
        myWriter.writeNonTerminal("VarDecl");
    }
}//变量声明
