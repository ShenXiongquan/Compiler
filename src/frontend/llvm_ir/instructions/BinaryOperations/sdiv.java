package frontend.llvm_ir.instructions.BinaryOperations;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.IntegerType;

/**
 * <result> = sdiv <ty> <op1>, <op2>	有符号除法
 */
public class sdiv extends BinaryOperation {

    public sdiv(Value op1, Value op2) {
        super(IntegerType.i32, op1, op2);
    }

    @Override
    public String ir() {
        return this.getName() + " = sdiv " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }

    @Override
    public String getMipsType() {
        return "div";
    }
}
