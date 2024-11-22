package frontend.node.stmt;

import frontend.Visitor;
import frontend.ir.BasicBlock;
import frontend.ir.instructions.ControlFlowInstructions.ControlFlowInstr;
import frontend.ir.instructions.ControlFlowInstructions.br;
import frontend.node.Cond;
import frontend.node.ForStmt;
import frontend.token.token;
import frontend.tool.myWriter;

public class LoopStmt extends Stmt {
    public token forToken;
    public token lparent;

    public ForStmt initForStmt;  // 这通常是一个赋值语句或者声明语句
    public token semicn1;
    public Cond forCondition;
    public token semicn2;
    public ForStmt updateForStmt;

    public token rparent;

    public Stmt forBody;

    @Override
    public void print() {
        forToken.print();
        lparent.print();
        if (initForStmt != null) initForStmt.print();
        semicn1.print();
        if (forCondition != null) forCondition.print();
        semicn2.print();
        if (updateForStmt != null) updateForStmt.print();
        rparent.print();
        forBody.print();
        myWriter.writeNonTerminal("Stmt");
    }

    @Override
    public void visit() {
        if (initForStmt != null) initForStmt.visit();

        // cond 块负责条件判断和跳转,如果是 true 则进入 bodyBlock,如果是 false 就进入 nextBlock,结束 for 语句
        // body 块是循环的主体
        BasicBlock condBlock = (forCondition == null ? null : new BasicBlock());
        BasicBlock bodyBlock = new BasicBlock();
        BasicBlock updateBlock = new BasicBlock();
        BasicBlock endBlock = new BasicBlock();

        if (updateForStmt != null) Visitor.continueToBlocks.add(updateBlock);
        else if (forCondition != null) Visitor.continueToBlocks.add(condBlock);
        else Visitor.continueToBlocks.add(bodyBlock);

        Visitor.breakToBlocks.add(endBlock);


        if (forCondition != null) {
            br br=new br(condBlock);
            Visitor.curBlock.addInstruction(br);
            Visitor.curBlock = condBlock;
            
            Visitor.curFunc.addBasicBlocks(Visitor.curBlock);
            Visitor.trueBlock.add(bodyBlock);
            Visitor.falseBlock.add(endBlock);
            forCondition.visit();
        }else{
            br br=new br(bodyBlock);
            Visitor.curBlock.addInstruction(br);
        }

        Visitor.curBlock = bodyBlock;
        
        Visitor.curFunc.addBasicBlocks(Visitor.curBlock);
        forBody.visit();
        br br=new br(updateBlock);
        Visitor.curBlock.addInstruction(br);
        Visitor.curBlock = updateBlock;
        
        Visitor.curFunc.addBasicBlocks(Visitor.curBlock);

        if (updateForStmt != null) {
            updateForStmt.visit();
        }

        br = new br(condBlock==null?bodyBlock:condBlock);
        Visitor.curBlock.addInstruction(br);

        Visitor.continueToBlocks.pop();
        Visitor.breakToBlocks.pop();

        Visitor.curBlock = endBlock;
        
        Visitor.curFunc.addBasicBlocks(Visitor.curBlock);
    }
}
