package frontend.node.unaryExp;


import frontend.node.primaryExp.PrimaryExp;

public class PunaryExp extends UnaryExp {
    public PrimaryExp primaryExp;

    @Override
    public String print() {
        return primaryExp.print() +
                "<UnaryExp>\n";
    }


    public void visit() {
        primaryExp.visit();
    }
}
