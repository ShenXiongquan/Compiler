package frontend.node.unaryExp;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.BinaryOperations.icmp;
import frontend.llvm_ir.instructions.BinaryOperations.sub;
import frontend.llvm_ir.type.IntegerType;
import frontend.node.UnaryOp;
import frontend.token.tokenType;
import frontend.tool.myWriter;

public class UnaryOpUE extends UnaryExp {
    public UnaryOp unaryOp;
    public UnaryExp unaryExp;

    @Override
    public void print() {
        unaryOp.print();
        unaryExp.print();
        myWriter.writeNonTerminal("UnaryExp");
    }

    public void visit() {
        unaryExp.visit();
        if (Visitor.calAble) {// 常量情况下直接计算
            Visitor.upConstValue = switch (unaryOp.op.type()) {
                case MINU -> -Visitor.upConstValue;
                case NOT -> (Visitor.upConstValue == 0) ? 1 : 0;
                default -> throw new IllegalArgumentException("Unexpected tokenType: " + unaryOp.op.type());
            };
        } else {
            Value operand = zext(Visitor.upValue); // 统一扩展操作数
            switch (unaryOp.op.type()) {
                case MINU -> {
                    if (operand instanceof ConstInt) {// 常量操作：负号
                        Visitor.upConstValue = -Visitor.upConstValue;
                        Visitor.upValue = new ConstInt(IntegerType.i32, Visitor.upConstValue);
                    } else {// 非常量操作：生成减法指令
                        Visitor.upValue = sub(ConstInt.zero, operand);
                    }
                }
                case NOT -> {
                    if (operand instanceof ConstInt) {// 常量操作：逻辑非
                        Visitor.upConstValue = (Visitor.upConstValue == 0) ? 1 : 0;
                        Visitor.upValue = new ConstInt(IntegerType.i1, Visitor.upConstValue);
                    } else {// 非常量操作：生成比较指令
                        Visitor.upValue = icmp(icmp.EQ, operand, ConstInt.zero);
                    }
                }
                default -> throw new IllegalArgumentException("Unexpected tokenType: " + unaryOp.op.type());
            }
        }

    }
}
