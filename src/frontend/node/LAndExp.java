package frontend.node;

import frontend.Visitor;
import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.BinaryOperations.icmp;
import frontend.llvm_ir.instructions.ControlFlowInstructions.br;
import frontend.token.token;
import frontend.tool.myWriter;

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

    public void visit() {
        if (lAndExp != null) {
            lAndExp.visit();
            Visitor.curBlock = Visitor.AndBlocks.get(Visitor.AndIndex).pop(); // 更新当前块
            eqExp.visit();
        } else { // 最底层逻辑
            if (Visitor.AndIndex != 0) {
                Visitor.curBlock = Visitor.AndBlocks.get(Visitor.AndIndex).pop();
            } // 更新为最左侧块
            eqExp.visit();
        }
        handleBr();
    }

    /**
     * 封装 br 和基本块跳转的逻辑
     */
    private void handleBr() {
        BasicBlock LorRightBlock = Visitor.LorBlocks.peekFirst(); // || 的右侧块

        BasicBlock AndRightBlock = Visitor.AndBlocks.get(Visitor.AndIndex).peekFirst(); // && 的右侧块


        br br;
        if (Visitor.upValue instanceof ConstInt constInt) {
            // 常量优化
            br = constInt.isZero() ? new br(LorRightBlock) : new br(AndRightBlock);
        } else {
            // 非常量逻辑
            if (Visitor.upValue.getType().isInt1()) {
                br = new br(Visitor.upValue, AndRightBlock, LorRightBlock);
            } else {
                icmp ICMP = new icmp(icmp.NE, zext(Visitor.upValue), ConstInt.zero); // 判断不为0
                Visitor.curBlock.addInstruction(ICMP);
                br = new br(ICMP, AndRightBlock, LorRightBlock);
            }
        }
        Visitor.curBlock.addInstruction(br); // 添加 br 指令
    }

    public void label() {
        if (lAndExp != null) {
            lAndExp.label();
            BasicBlock block = new BasicBlock("Block_and"+Function.andNum++);
            Visitor.curFunc.addBasicBlock(block);
            Visitor.AndBlocks.get(Visitor.AndBlocks.size() - 1).add(block);
        } else {//最左侧,第一个eqExp
            if (Visitor.AndBlocks.size() != 1) {
                BasicBlock block = new BasicBlock("Block_or"+ Function.orNum++);
                Visitor.curFunc.addBasicBlock(block);
                Visitor.AndBlocks.get(Visitor.AndBlocks.size() - 1).add(block);
                Visitor.upValue = block;
            }
        }
    }
}//逻辑与表达式
