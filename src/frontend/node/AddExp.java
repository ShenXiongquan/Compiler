package frontend.node;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.BinaryOperations.add;
import frontend.llvm_ir.instructions.BinaryOperations.sub;
import frontend.llvm_ir.type.IntegerType;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.myWriter;

public class AddExp extends node {
    public AddExp addExp;
    public MulExp mulExp;
    public token op;

    public void print() {
        if (addExp != null) {
            addExp.print();
            op.print();
        }
        mulExp.print();
        myWriter.writeNonTerminal("AddExp");
    }

    public void visit() {
        if (Visitor.calAble) {
            if (addExp != null) {
                addExp.visit();
                int l = Visitor.upConstValue;
                mulExp.visit();
                int r = Visitor.upConstValue;
                Visitor.upConstValue = switch (op.type()) {
                    case PLUS -> l + r;
                    case MINU -> l - r;
                    default -> throw new IllegalArgumentException("Unexpected tokenType: " + op.type());
                };
            } else {
                mulExp.visit();
            }
        } else {
            if (addExp != null) {
                addExp.visit();
                Value lhs = zext(Visitor.upValue);
                int l = Visitor.upConstValue;
                mulExp.visit();
                Value rhs = zext(Visitor.upValue);
                int r = Visitor.upConstValue;
                if (lhs instanceof ConstInt && rhs instanceof ConstInt) {// 处理常量操作
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
    }
}//加减表达式
