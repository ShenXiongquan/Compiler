package frontend.nonTerminal;

import frontend.token.token;
import frontend.tool.myWriter;
import java.util.ArrayList;
import java.util.List;

public class Block {
    public token lbrace;
    public final List<BlockItem> blockItems=new ArrayList<>();
    public token rbrace;

    public void visit(){
        lbrace.visit();
        if(!blockItems.isEmpty()) for (BlockItem blockItem:blockItems) blockItem.visit();
        rbrace.visit();
        myWriter.writeNonTerminal("Block");
    }
}
