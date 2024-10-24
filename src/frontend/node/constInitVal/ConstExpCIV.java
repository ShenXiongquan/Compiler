package frontend.node.constInitVal;

import frontend.node.ConstExp;
import frontend.tool.myWriter;

public class ConstExpCIV extends ConstInitVal{
    public ConstExp constExp;

    @Override
    public void visit() {
        constExp.visit();
        myWriter.writeNonTerminal("ConstInitVal");
    }
}
