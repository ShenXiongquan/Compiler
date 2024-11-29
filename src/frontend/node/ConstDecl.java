package frontend.node;


import frontend.token.token;

import java.util.ArrayList;
import java.util.List;

public class ConstDecl extends node {
    public token con;
    public BType bType;
    public final List<ConstDef> constDefs = new ArrayList<>();

    public final List<token> comma = new ArrayList<>();

    public token semicn;

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(con.print());
        sb.append(bType.print());
        sb.append(constDefs.get(0).print());
        for (int i = 1; i < constDefs.size(); i++) {
            sb.append(comma.get(i - 1).print());
            sb.append(constDefs.get(i).print());
        }
        if (semicn != null) {
            sb.append(semicn.print());
        }
        sb.append("<ConstDecl>\n");
        return sb.toString();
    }

    public void visit() {
        bType.visit();
        constDefs.forEach(ConstDef::visit);
    }
}//常量声明
