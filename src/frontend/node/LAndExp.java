package frontend.node;

import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.BinaryOperations.icmp;
import frontend.token.token;

public class LAndExp extends node {
    public EqExp eqExp;
    public token and;
    public LAndExp lAndExp;

    public String print() {
        StringBuilder sb = new StringBuilder();
        if (lAndExp != null) {
            sb.append(lAndExp.print());
            sb.append(and.print());
        }
        sb.append(eqExp.print());
        sb.append("<LAndExp>\n");
        return sb.toString();
    }


    public void handle() {
        if (lAndExp != null) {
            lAndExp.handle();
            Visitor.eqExps.add(eqExp);
        } else {
            Visitor.eqExps.add(eqExp);
        }
    }

    public void visit(BasicBlock trueBlock, BasicBlock falseBlock) {
        int size = Visitor.eqExps.size();
        int i = 0;
        for (EqExp eqExp : Visitor.eqExps) {
            BasicBlock nextBlock = (size == (++i) ? trueBlock : new BasicBlock());
            eqExp.visit();

            if (Visitor.upValue instanceof ConstInt constInt) {// 常量直接判断是否为零
                br(constInt.isZero() ? falseBlock : nextBlock);
            } else {// 处理非常量
                icmp condition = Visitor.upValue.getType().isInt1()
                        ? (icmp) Visitor.upValue // 直接使用 int1 类型值作为条件
                        : icmp(icmp.NE, zext(Visitor.upValue), ConstInt.zero); // 非 int1 类型需要扩展和比较
                br(condition, nextBlock, falseBlock);
            }

            if (i != size) {
                enterNewBlock(nextBlock);
            }
        }
    }

}//逻辑与表达式
