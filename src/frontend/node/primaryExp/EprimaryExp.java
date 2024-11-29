package frontend.node.primaryExp;

import frontend.node.Exp;
import frontend.token.token;

public class EprimaryExp extends PrimaryExp {
    public token lparent;

    public Exp exp;

    public token rparent;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(lparent.print());
        sb.append(exp.print());
        if (rparent != null) sb.append(rparent.print());
        sb.append("<PrimaryExp>\n");
        return sb.toString();
    }

    public void visit() {
        exp.visit();
    }
}
