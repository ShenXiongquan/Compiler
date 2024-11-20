package frontend.node;

import frontend.node.blockItem.BlockItem;
import frontend.token.token;
import frontend.tool.myWriter;
import java.util.ArrayList;
import java.util.List;

public class Block extends node{
    public token lbrace;
    public final List<BlockItem> blockItems=new ArrayList<>();
    public token rbrace;

    public void print(){
        lbrace.print();
        if(!blockItems.isEmpty()) for (BlockItem blockItem:blockItems) blockItem.print();
        rbrace.print();
        myWriter.writeNonTerminal("Block");
    }

    @Override
    public void visit() {
        for(BlockItem blockItem:blockItems){
            blockItem.visit();
        }
    }
}
