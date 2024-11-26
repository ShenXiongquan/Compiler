package frontend.node.stmt;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.instructions.ControlFlowInstructions.br;
import frontend.node.Cond;

import frontend.token.token;
import frontend.tool.myWriter;

public class IfStmt extends Stmt {
    public token ifToken;
    public token lparent;
    public Cond cond;

    public token rparent;
    public Stmt trueStmt;
    public token elseToken;
    public Stmt falseStmt;  // 这可能为null，如果没有else分支的话

    @Override
    public void print() {
        ifToken.print();
        lparent.print();
        cond.print();
        if (rparent != null) rparent.print();
        trueStmt.print();
        if (elseToken != null) {
            elseToken.print();
            falseStmt.print();
        }
        myWriter.writeNonTerminal("Stmt");
    }
    public void visit() {
        BasicBlock trueBlock=new BasicBlock("Block_true"+ Function.ifNum++);
        BasicBlock endBlock=new BasicBlock("Block_next"+Function.ifNum);
        BasicBlock falseBlock=(falseStmt!=null)?new BasicBlock("Block_false"+Function.ifNum):endBlock;


       //if()
        cond.visit(trueBlock,falseBlock);

        Visitor.curBlock=trueBlock;
        Visitor.curFunc.addBasicBlock(Visitor.curBlock);
        trueStmt.visit();
        br br=new br(endBlock);
        Visitor.curBlock.addInstruction(br);

        if(falseStmt!=null){//else{}
            Visitor.curBlock=falseBlock;
            Visitor.curFunc.addBasicBlock(Visitor.curBlock);
            falseStmt.visit();
            br=new br(endBlock);
            Visitor.curBlock.addInstruction(br);
        }


        Visitor.curBlock=endBlock;
        Visitor.curFunc.addBasicBlock(Visitor.curBlock);
    }
}