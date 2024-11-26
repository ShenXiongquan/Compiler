package frontend.node;

import frontend.Visitor;
import frontend.tool.myWriter;

import java.util.ArrayList;

public class Cond extends node{
    public  LOrExp lOrExp;
    public void print(){
        lOrExp.print();
        myWriter.writeNonTerminal("Cond");
    }
    public void visit() {
        Visitor.AndBlocks=new ArrayList<>();
        lOrExp.label();
        Visitor.trueBlock.pop();
        Visitor.LorBlocks.add(Visitor.falseBlock.pop());

        lOrExp.visit();
    }
}//条件表达式
