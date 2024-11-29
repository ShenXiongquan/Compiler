package frontend.node.primaryExp;

import frontend.node.Character;

public class CprimaryExp extends PrimaryExp {
    public Character character;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(character.print());
        sb.append("<PrimaryExp>\n");
        return sb.toString();
    }

    public void visit() {
        character.visit();
    }
}
