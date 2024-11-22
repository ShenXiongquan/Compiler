package frontend.node;


import frontend.Visitor;
import frontend.ir.type.IntegerType;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class ConstDecl extends node{
    public token _const;
    public BType bType ;
    public final List<ConstDef> constDefs = new ArrayList<>();

    public List<token> comma=new ArrayList<>();

    public token semicn;

    public void print(){
        _const.print();
        bType.print();
        constDefs.get(0).print();
        for (int i=1;i<constDefs.size();i++){
            comma.get(i-1).print();
            constDefs.get(i).print();
        }
        if(semicn!=null)semicn.print();
        myWriter.writeNonTerminal("ConstDecl");
    }
    @Override
    public void visit() {
        bType.visit();
        constDefs.forEach(ConstDef::visit);
    }
}//常量声明
