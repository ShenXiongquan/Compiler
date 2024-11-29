package frontend.node.primaryExp;

import frontend.node.Number;

public class NprimaryExp extends PrimaryExp {
    public Number number;

    @Override
    public String print() {
        return number.print() +
                "<PrimaryExp>\n";
    }

    public void visit() {
        number.visit();
    }
}
