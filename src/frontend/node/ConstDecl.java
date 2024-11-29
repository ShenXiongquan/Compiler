package frontend.node;


import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class ConstDecl extends node{
    public token con;
    public BType bType;
    public final List<ConstDef> constDefs = new ArrayList<>();

    public final List<token> comma=new ArrayList<>();

    public token semicn;

    public void print(){
        con.print();
        bType.print();
        constDefs.get(0).print();
        for (int i=1;i<constDefs.size();i++){
            comma.get(i-1).print();
            constDefs.get(i).print();
        }
        if(semicn!=null)semicn.print();
        myWriter.writeNonTerminal("ConstDecl");
    }
    public void visit() {
        bType.visit();
        constDefs.forEach(ConstDef::visit);
    }
}//常量声明
