package frontend.ir.instructions.BinaryOperations;

import frontend.ir.Value;
import frontend.ir.type.IntegerType;

/**
 * or	<result> = or <ty> <op1>, <op2>	按位或
 */
public class or extends BinaryOperation{

    public or( Value op1, Value op2){
        super(IntegerType.i1, op1, op2);
    }

    @Override
    public String ir() {
        return this.getName() + " = or " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }
}
