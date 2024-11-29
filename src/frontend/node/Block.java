package frontend.node;

import frontend.node.blockItem.BlockItem;
import frontend.token.token;

import java.util.ArrayList;
import java.util.List;

public class Block extends node {
    public token lbrace;
    public final List<BlockItem> blockItems = new ArrayList<>();
    public token rbrace;

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(lbrace.print());
        for (BlockItem blockItem : blockItems) {
            sb.append(blockItem.print());
        }
        sb.append(rbrace.print());
        sb.append("<Block>\n");
        return sb.toString();
    }

    public void visit() {
        for (BlockItem blockItem : blockItems) {
            blockItem.visit();
        }
    }
}
