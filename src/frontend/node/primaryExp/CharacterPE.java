package frontend.node.primaryExp;

import frontend.node.Character;
import frontend.tool.myWriter;

public class CharacterPE extends PrimaryExp{
    public Character character;

    @Override
    public void visit() {
        character.visit();
        myWriter.writeNonTerminal("PrimaryExp");
    }
}
