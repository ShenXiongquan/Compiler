package frontend.llvm_ir.instructions.BinaryOperations;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.constants.Constant;
import frontend.llvm_ir.type.IntegerType;

/**
 * <result> = sub <ty> <op1>, <op2>
 */
public class sub extends BinaryOperation {
    public sub(Value op1, Value op2) {
        super(IntegerType.i32, op1, op2);
    }

    @Override
    public String ir() {
        return this.getName() + " = sub " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }

    @Override
    public String getMipsType() {
        return getOp2() instanceof Constant ? "subiu" : "subu";
    }
}
