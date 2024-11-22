package frontend.ir.instructions.BinaryOperations;

import frontend.ir.Value;
import frontend.ir.type.IntegerType;

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
