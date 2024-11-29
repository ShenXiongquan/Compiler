package frontend.node.primaryExp;


import frontend.node.LVal;

public class LprimaryExp extends PrimaryExp {
    public LVal lVal;

    @Override
    public String print() {
        return lVal.print() +
                "<PrimaryExp>\n";
    }

    public void visit() {
        lVal.visit();
    }
}
