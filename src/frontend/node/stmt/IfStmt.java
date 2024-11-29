package frontend.node.stmt;

import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.node.Cond;
import frontend.token.token;

public class IfStmt extends Stmt {
    public token ifToken;
    public token lparent;
    public Cond cond;

    public token rparent;
    public Stmt trueStmt;
    public token elseToken;
    public Stmt falseStmt;  // 这可能为null，如果没有else分支的话

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(ifToken.print());
        sb.append(lparent.print());
        sb.append(cond.print());
        if (rparent != null) sb.append(rparent.print());
        sb.append(trueStmt.print());
        if (elseToken != null) {
            sb.append(elseToken.print());
            sb.append(falseStmt.print());
        }
        sb.append("<Stmt>\n");
        return sb.toString();
    }

    public void visit() {
        BasicBlock trueBlock = new BasicBlock("Block_true" + Function.ifNum++);
        BasicBlock endBlock = new BasicBlock("Block_next" + Function.ifNum);
        BasicBlock falseBlock = (falseStmt != null) ? new BasicBlock("Block_false" + Function.ifNum) : endBlock;


        //if()
        cond.visit(trueBlock, falseBlock);
        enterNewBlock(trueBlock);
        trueStmt.visit();
        br(endBlock);


        if (falseStmt != null) {//else{}
            enterNewBlock(falseBlock);

            falseStmt.visit();
            br(endBlock);
        }

        enterNewBlock(endBlock);
    }
}