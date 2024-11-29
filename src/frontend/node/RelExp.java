package frontend.node;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.BinaryOperations.icmp;
import frontend.llvm_ir.type.IntegerType;
import frontend.token.token;

public class RelExp extends node {

    public AddExp addExp;
    public RelExp relExp;
    public token relOp;//'<' | '>' | '<=' | '>='

    public String print() {
        StringBuilder sb = new StringBuilder();
        if (relExp != null) {
            sb.append(relExp.print());
            sb.append(relOp.print());
        }
        sb.append(addExp.print());
        sb.append("<RelExp>\n");
        return sb.toString();
    }

    public void visit() {
        if (relExp != null) {
            relExp.visit();
            Value lhs = zext(Visitor.upValue);
            int lConst = Visitor.upConstValue;
            addExp.visit();
            Value rhs = zext(Visitor.upValue);
            int rConst = Visitor.upConstValue;
            if (lhs instanceof ConstInt && rhs instanceof ConstInt) {
                System.out.println("左值:" + lhs.getName() + " " + lConst + "右值:" + rhs.getName() + " " + rConst);
                // 处理常量比较
                switch (relOp.type()) {
                    case LSS -> Visitor.upConstValue = (lConst < rConst) ? 1 : 0;
                    case LEQ -> Visitor.upConstValue = (lConst <= rConst) ? 1 : 0;
                    case GRE -> Visitor.upConstValue = (lConst > rConst) ? 1 : 0;
                    case GEQ -> Visitor.upConstValue = (lConst >= rConst) ? 1 : 0;
                    default -> throw new IllegalArgumentException("Unexpected tokenType: " + relOp.type());
                }
                System.out.println("比较结果值：" + Visitor.upConstValue);
                Visitor.upValue = new ConstInt(IntegerType.i1, Visitor.upConstValue); // 设置布尔类型的常量值
            } else {
                // 处理非常量的比较
                String icmpType;
                switch (relOp.type()) {
                    case LSS -> icmpType = icmp.LT;
                    case LEQ -> icmpType = icmp.LE;
                    case GRE -> icmpType = icmp.GT;
                    case GEQ -> icmpType = icmp.GE;
                    default -> throw new IllegalArgumentException("Unexpected tokenType: " + relOp.type());
                }
                Visitor.upValue = icmp(icmpType, lhs, rhs);
            }

        } else {
            addExp.visit();
        }
    }
}//关系表达式
