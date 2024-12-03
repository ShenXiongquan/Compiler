package frontend.node.primaryExp;

import frontend.node.Character;

public class CprimaryExp extends PrimaryExp {
    public Character character;

    @Override
    public String print() {
        return character.print() +
                "<PrimaryExp>\n";
    }

    public void visit() {
        character.visit();
    }
}
