package frontend.node;

public class Exp extends node {
    public AddExp addExp;

    public String print() {
        return addExp.print() +
                "<Exp>\n";
    }

    public void visit() {
        addExp.visit();
    }
}//表达式
