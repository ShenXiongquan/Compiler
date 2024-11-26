package frontend.llvm_ir.instructions.BinaryOperations;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.IntegerType;

/**
 *and	<result> = and <ty> <op1>, <op2>	按位与
 */
public class and extends BinaryOperation{

    public and( Value op1, Value op2){
        super(IntegerType.i1, op1, op2);
    }

    @Override
    public String ir() {
        return this.getName() + " = and " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }
}
