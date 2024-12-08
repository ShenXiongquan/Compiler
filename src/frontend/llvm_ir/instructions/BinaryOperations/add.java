package frontend.llvm_ir.instructions.BinaryOperations;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.constants.Constant;
import frontend.llvm_ir.type.IntegerType;

/**
 * <result> = add <ty> <op1>, <op2>
 */
public class add extends BinaryOperation {
    public add(Value op1, Value op2) {
        super(IntegerType.i32, op1, op2);
    }

    @Override
    public String ir() {
        return this.getName() + " = add " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }


    @Override
    public String getMipsType() {
        return getOp2() instanceof Constant ? "addiu" : "addu";
    }
}
