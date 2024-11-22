package frontend.node;

import frontend.Visitor;
import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.instructions.BinaryOperations.icmp;
import frontend.ir.instructions.ControlFlowInstructions.br;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayDeque;

public class LOrExp extends node{
    public LAndExp lAndExp;
    public token or;
    public LOrExp lOrExp;

    public void print(){
        if(lOrExp!=null){
            lOrExp.print();
            or.print();
        }
        lAndExp.print();
        myWriter.writeNonTerminal("LOrExp");
    }
    @Override
    public void visit() {
        //  leftBlock||rightBlock
        if(lOrExp!=null){//进行递归
            lOrExp.visit();
            Visitor.AndIndex++;
            lAndExp.visit();
            Visitor.LorBlocks.pop();
        }else{//递归到最底层了,该块是最左侧块
            Visitor.AndIndex=0;
            lAndExp.visit();
            Visitor.LorBlocks.pop();
        }
    }
    //先打上标签
    public void label() {
        //  leftBlock||rightBlock
        if(lOrExp!=null){//进行递归
            lOrExp.label();
            ArrayDeque<BasicBlock> AndBlockDeque=new ArrayDeque<>();
            Visitor.AndBlocks.add(AndBlockDeque);
            lAndExp.label();
            AndBlockDeque.add(Visitor.trueBlock.peek());
            Visitor.LorBlocks.add((BasicBlock) Visitor.upValue);
        }else{//最左侧,第一个lAndExp
            ArrayDeque<BasicBlock> AndBlockDeque=new ArrayDeque<>();
            Visitor.AndBlocks.add(AndBlockDeque);
            lAndExp.label();
            AndBlockDeque.add(Visitor.trueBlock.peek());
        }
    }
}//逻辑或表达式
