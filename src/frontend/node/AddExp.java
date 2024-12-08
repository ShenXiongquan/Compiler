package frontend.node;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.type.IntegerType;
import frontend.token.token;

public class AddExp extends node {
    public AddExp addExp;
    public MulExp mulExp;
    public token op;

    public String print() {
        StringBuilder sb = new StringBuilder();
        if (addExp != null) {
            sb.append(addExp.print());
            sb.append(op.print());
        }
        sb.append(mulExp.print());
        sb.append("<AddExp>\n");
        return sb.toString();
    }


    public void visit() {

        if (addExp != null) {
            addExp.visit();
            Value lhs = zext(Visitor.upValue);
            int l = Visitor.upConstValue;
            mulExp.visit();
            Value rhs = zext(Visitor.upValue);
            int r = Visitor.upConstValue;
            if (lhs instanceof ConstInt && rhs instanceof ConstInt || Visitor.calAble) {// 处理常量操作
                Visitor.upConstValue = switch (op.type()) {
                    case PLUS -> l + r;
                    case MINU -> l - r;
                    default -> throw new IllegalArgumentException("Unexpected tokenType: " + op.type());
                };
                Visitor.upValue = new ConstInt(IntegerType.i32, Visitor.upConstValue);
            } else {// 处理非常量操作
                Visitor.upValue = switch (op.type()) {
                    case PLUS -> add(lhs, rhs);
                    case MINU -> sub(lhs, rhs);
                    default -> throw new IllegalArgumentException("Unexpected tokenType: " + op.type());
                };
            }
        } else {
            mulExp.visit();
        }

    }
}//加减表达式
