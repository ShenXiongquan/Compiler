package frontend.node;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.BinaryOperations.icmp;
import frontend.llvm_ir.instructions.ControlFlowInstructions.br;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;

public class LAndExp extends node {
    public EqExp eqExp;
    public token and;
    public LAndExp lAndExp;



    public void print() {
        if (lAndExp != null) {
            lAndExp.print();
            and.print();
        }
        eqExp.print();
        myWriter.writeNonTerminal("LAndExp");
    }

    public void handle(){
        if (lAndExp != null) {
            lAndExp.handle();
            Visitor.eqExps.add(eqExp);
        } else {
            Visitor.eqExps.add(eqExp);
        }
    }

    public void visit(BasicBlock trueBlock,BasicBlock falseBlock){
        int size=Visitor.eqExps.size();
        int i=0;
        for(EqExp eqExp:Visitor.eqExps){
            BasicBlock nextBlock=(size==(++i)?trueBlock:new BasicBlock("Block_and"+Function.andNum++));
            eqExp.visit();
            br br;
            if (Visitor.upValue instanceof ConstInt constInt) {
                br = constInt.isZero() ? new br(falseBlock) : new br(nextBlock);
            } else {
                if (Visitor.upValue.getType().isInt1()) {//如果是int1类型
                    br = new br(Visitor.upValue, nextBlock, falseBlock);
                } else {
                    icmp ICMP = new icmp(icmp.NE, zext(Visitor.upValue), ConstInt.zero);
                    Visitor.curBlock.addInstruction(ICMP);
                    br = new br(ICMP,nextBlock ,falseBlock);
                }
            }
            Visitor.curBlock.addInstruction(br); // 添加 br 指令
            if(i!=size){
                Visitor.curBlock =nextBlock;
                Visitor.curFunc.addBasicBlock(Visitor.curBlock);
            }
        }
    }

}//逻辑与表达式
