package frontend.node;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.type.IntegerType;
import frontend.node.unaryExp.UnaryExp;
import frontend.token.token;

public class MulExp extends node {

    public MulExp mulExp;
    public token op;
    public UnaryExp unaryExp;

    public String print() {
        StringBuilder sb = new StringBuilder();
        if (mulExp != null) {
            sb.append(mulExp.print());
            sb.append(op.print());
        }
        sb.append(unaryExp.print());
        sb.append("<MulExp>\n");
        return sb.toString();
    }

    public void visit() {

        if (mulExp != null) {
            mulExp.visit();
            Value lhs = zext(Visitor.upValue);
            int l = Visitor.upConstValue;
            unaryExp.visit();
            Value rhs = zext(Visitor.upValue);
            int r = Visitor.upConstValue;
            if (lhs instanceof ConstInt && rhs instanceof ConstInt || Visitor.calAble) {// 处理常量操作
                Visitor.upConstValue = switch (op.type()) {
                    case MULT -> l * r;
                    case DIV -> l / r;
                    case MOD -> l % r;
                    default -> throw new IllegalArgumentException("Unexpected tokenType: " + op.type());
                };
                Visitor.upValue = new ConstInt(IntegerType.i32, Visitor.upConstValue);
            } else {// 处理非常量操作
                Visitor.upValue = switch (op.type()) {
                    case MULT -> mul(lhs, rhs);
                    case DIV -> sdiv(lhs, rhs);
                    case MOD -> srem(lhs, rhs);
                    default -> throw new IllegalArgumentException("Unexpected tokenType: " + op.type());
                };
            }
        } else {
            unaryExp.visit();
        }

    }
}//乘除模表达式
