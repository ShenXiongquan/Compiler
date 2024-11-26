package frontend.llvm_ir.instructions.BinaryOperations;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.IntegerType;

/**
 * <result> = or <ty> <op1>, <op2>
 */
public class mul extends BinaryOperation{

    public mul(Value op1, Value op2){
        super(IntegerType.i32, op1, op2);
    }

    @Override
    public String ir() {
        return this.getName() + " = mul " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }
}
