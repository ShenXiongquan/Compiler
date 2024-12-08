package frontend.node;

import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Visitor;
import frontend.token.token;

import java.util.ArrayList;

public class LOrExp extends node {
    public LAndExp lAndExp;
    public token or;
    public LOrExp lOrExp;

    public String print() {
        StringBuilder sb = new StringBuilder();
        if (lOrExp != null) {
            sb.append(lOrExp.print());
            sb.append(or.print());
        }
        sb.append(lAndExp.print());
        sb.append("<LOrExp>\n");
        return sb.toString();
    }


    public void handle() {
        if (lOrExp != null) {
            lOrExp.handle();
            Visitor.lAndExps.add(lAndExp);
        } else {
            Visitor.lAndExps.add(lAndExp);
        }
    }

    public void visit(BasicBlock trueBlock, BasicBlock falseBlock) {
        int i = 0;
        int size = Visitor.lAndExps.size();
        for (LAndExp lAndExp : Visitor.lAndExps) {
            BasicBlock nextBlock = (size == (++i) ? falseBlock : new BasicBlock());
            Visitor.eqExps = new ArrayList<>();
            lAndExp.handle();
            lAndExp.visit(trueBlock, nextBlock);
            if (i != size) {
                enterNewBlock(nextBlock);
            }
        }
    }

}//逻辑或表达式
