package frontend.node;

import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Visitor;

import java.util.ArrayList;

public class Cond extends node {
    public LOrExp lOrExp;

    public String print() {
        return lOrExp.print() +
                "<Cond>\n";
    }


    public void visit(BasicBlock trueBlock, BasicBlock falsBlock) {
        Visitor.lAndExps = new ArrayList<>();
        lOrExp.handle();
        lOrExp.visit(trueBlock, falsBlock);
    }
}//条件表达式
