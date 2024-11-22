package frontend.node.stmt;

import frontend.Visitor;
import frontend.ir.BasicBlock;
import frontend.ir.instructions.ControlFlowInstructions.br;
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
    @Override
    public void visit() {
        BasicBlock trueBlock=new BasicBlock();
        BasicBlock endBlock=new BasicBlock();
        BasicBlock falseBlock=(falseStmt!=null)?new BasicBlock():endBlock;
        Visitor.trueBlock.add(trueBlock);
        Visitor.falseBlock.add(falseBlock);
       //if()
        cond.visit();

        Visitor.curBlock=trueBlock;
        Visitor.curFunc.addBasicBlocks(Visitor.curBlock);
        trueStmt.visit();
        br br=new br(endBlock);
        Visitor.curBlock.addInstruction(br);

        if(falseStmt!=null){//else{}
            Visitor.curBlock=falseBlock;
            Visitor.curFunc.addBasicBlocks(Visitor.curBlock);
            falseStmt.visit();
            br=new br(endBlock);
            Visitor.curBlock.addInstruction(br);
        }


        Visitor.curBlock=endBlock;
        Visitor.curFunc.addBasicBlocks(Visitor.curBlock);
    }
}