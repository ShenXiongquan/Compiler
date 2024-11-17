package frontend.node.primaryExp;

import frontend.Visitor;
import frontend.node.Character;
import frontend.tool.myWriter;

public class CharacterPE extends PrimaryExp{
    public Character character;

    @Override
    public void print() {
        character.print();
        myWriter.writeNonTerminal("PrimaryExp");
    }

    @Override
    public void visit() {
        character.visit();
    }
}
