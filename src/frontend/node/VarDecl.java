package frontend.node;

import frontend.token.token;

import java.util.ArrayList;
import java.util.List;

public class VarDecl extends node {

    public BType bType;
    public final List<VarDef> varDefs = new ArrayList<>();
    public final List<token> comma = new ArrayList<>();

    public token semicn;

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(bType.print());
        sb.append(varDefs.get(0).print());
        for (int i = 1; i < varDefs.size(); i++) {
            sb.append(comma.get(i - 1).print());
            sb.append(varDefs.get(i).print());
        }
        if (semicn != null) {
            sb.append(semicn.print());
        }
        sb.append("<VarDecl>\n");
        return sb.toString();
    }

    public void visit() {
        bType.visit();
        varDefs.forEach(VarDef::visit);
    }
}//变量声明
