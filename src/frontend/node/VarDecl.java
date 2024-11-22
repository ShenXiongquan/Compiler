package frontend.node;

import frontend.Visitor;
import frontend.ir.type.IntegerType;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class VarDecl extends node {

    public BType bType;
    public final List<VarDef> varDefs = new ArrayList<>();
    public List<token> comma=new ArrayList<>();

    public token semicn;

    public void print() {
        bType.print();
        varDefs.get(0).print();
        for (int i = 1; i < varDefs.size(); i++) {
            comma.get(i-1).print();
            varDefs.get(i).print();
        }
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("VarDecl");
    }
    @Override
    public void visit() {
        bType.visit();

        varDefs.forEach(VarDef::visit);
    }
}//变量声明
