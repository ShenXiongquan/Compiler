package frontend.llvm_ir.instructions.BinaryOperations;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.IntegerType;

/**
 * srem	<result> = srem <type> <op1>, <op2>	有符号取余
 */
public class srem extends BinaryOperation {

    public srem(Value op1, Value op2) {
        super(IntegerType.i32, op1, op2);
    }

    @Override
    public String ir() {
        return this.getName() + " = srem " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }

    @Override
    public String getMipsType() {
        return "rem";
    }
}
