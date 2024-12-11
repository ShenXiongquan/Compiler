package frontend.node.unaryExp;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.BinaryOperations.icmp;
import frontend.llvm_ir.type.IntegerType;
import frontend.node.UnaryOp;

public class OunaryExp extends UnaryExp {
    public UnaryOp unaryOp;
    public UnaryExp unaryExp;

    @Override
    public String print() {
        return unaryOp.print() +
                unaryExp.print() +
                "<UnaryExp>\n";
    }


    public void visit() {
        unaryExp.visit();
        Value operand = zext(Visitor.upValue); // 统一扩展操作数
        // 处理逻辑分支
        switch (unaryOp.op.type()) {
            case MINU -> {
                if (Visitor.calAble || operand instanceof ConstInt) { // 常量逻辑
                    Visitor.upConstValue = -Visitor.upConstValue;
                    Visitor.upValue = new ConstInt(IntegerType.i32, Visitor.upConstValue);
                } else { // 非常量逻辑
                    Visitor.upValue = sub(ConstInt.zero, operand);
                }
            }
            case NOT -> {
                if (Visitor.calAble || operand instanceof ConstInt) { // 常量逻辑
                    Visitor.upConstValue = (Visitor.upConstValue == 0) ? 1 : 0;
                    Visitor.upValue = new ConstInt(IntegerType.i1, Visitor.upConstValue);
                } else { // 非常量逻辑
                    Visitor.upValue = icmp(icmp.EQ, operand, ConstInt.zero);
                }
            }
            default -> {
                Visitor.upValue = operand;
            }
        }
    }

}
