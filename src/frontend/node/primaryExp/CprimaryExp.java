package frontend.node.primaryExp;

import frontend.node.Character;
import frontend.tool.myWriter;

public class CprimaryExp extends PrimaryExp {
    public Character character;

    @Override
    public void print() {
        character.print();
        myWriter.writeNonTerminal("PrimaryExp");
    }

    public void visit() {
        character.visit();
    }
}
