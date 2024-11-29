package frontend.node.stmt;

import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.Visitor;
import frontend.node.Cond;
import frontend.node.ForStmt;
import frontend.token.token;

public class LoopStmt extends Stmt {
    public token forToken;
    public token lparent;

    public ForStmt initForStmt;  // 这通常是一个赋值语句或者声明语句
    public token semicn1;
    public Cond cond;
    public token semicn2;
    public ForStmt updateForStmt;

    public token rparent;

    public Stmt forBody;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(forToken.print());
        sb.append(lparent.print());
        if (initForStmt != null) sb.append(initForStmt.print());
        sb.append(semicn1.print());
        if (cond != null) sb.append(cond.print());
        sb.append(semicn2.print());
        if (updateForStmt != null) sb.append(updateForStmt.print());
        sb.append(rparent.print());
        sb.append(forBody.print());
        sb.append("<Stmt>\n");
        return sb.toString();
    }

    public void visit() {
        if (initForStmt != null) initForStmt.visit();

        // cond 块负责条件判断和跳转,如果是 true 则进入 bodyBlock,如果是 false 就进入 nextBlock,结束 for 语句
        // body 块是循环的主体
        BasicBlock bodyBlock = new BasicBlock("Block_body" + Function.forNum++);
        BasicBlock condBlock = (cond == null ? null : new BasicBlock("Block_cond" + Function.forNum));
        BasicBlock updateBlock = new BasicBlock("Block_update" + Function.forNum);
        BasicBlock endBlock = new BasicBlock("Block_end" + Function.forNum);

        //解决循环嵌套的问题
        Visitor.continueToBlocks.add(updateForStmt != null ? updateBlock : (cond != null ? condBlock : bodyBlock));
        Visitor.breakToBlocks.add(endBlock);

        if (cond != null) {
            br(condBlock);
            enterNewBlock(condBlock);
            cond.visit(bodyBlock, endBlock);
        } else {
            br(bodyBlock);

        }

        enterNewBlock(bodyBlock);

        forBody.visit();
        br(updateBlock);

        enterNewBlock(updateBlock);

        if (updateForStmt != null) {
            updateForStmt.visit();
        }

        br(condBlock == null ? bodyBlock : condBlock);


        Visitor.continueToBlocks.pop();
        Visitor.breakToBlocks.pop();

        enterNewBlock(endBlock);

    }
}
