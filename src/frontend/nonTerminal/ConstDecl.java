package frontend.nonTerminal;


import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class ConstDecl {
    public token Const;
    public BType bType ;
    public final List<ConstDef> constDefs = new ArrayList<>();
    public token comma;

    public token semicn;

    public void visit(){
        Const.visit();
        bType.visit();
        constDefs.get(0).visit();
        for (int i=1;i<constDefs.size();i++){
            comma.visit();
            constDefs.get(i).visit();
        }
        if(semicn!=null)semicn.visit();
        myWriter.writeNonTerminal("ConstDecl");
    }

}//常量声明
