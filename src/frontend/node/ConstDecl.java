package frontend.node;


import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class ConstDecl {
    public token _const;
    public BType bType ;
    public final List<ConstDef> constDefs = new ArrayList<>();

    public List<token> comma=new ArrayList<>();

    public token semicn;

    public void visit(){
        _const.visit();
        bType.visit();
        constDefs.get(0).visit();
        for (int i=1;i<constDefs.size();i++){
            comma.get(i-1).visit();
            constDefs.get(i).visit();
        }
        if(semicn!=null)semicn.visit();
        myWriter.writeNonTerminal("ConstDecl");
    }

}//常量声明
