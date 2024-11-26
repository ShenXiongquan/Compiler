package frontend.node;

import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Visitor;
import frontend.tool.myWriter;

import java.util.ArrayList;

public class Cond extends node{
    public  LOrExp lOrExp;
    public void print(){
        lOrExp.print();
        myWriter.writeNonTerminal("Cond");
    }

    public void visit(BasicBlock trueBlock,BasicBlock falsBlock) {
        Visitor.lAndExps=new ArrayList<>();
        lOrExp.handle();
        lOrExp.visit(trueBlock,falsBlock);
    }
}//条件表达式
