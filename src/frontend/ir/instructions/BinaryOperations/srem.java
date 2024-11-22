package frontend.ir.instructions.BinaryOperations;

import frontend.ir.Value;
import frontend.ir.type.IntegerType;

/**
 * srem	<result> = srem <type> <op1>, <op2>	有符号取余
 */
public class srem extends BinaryOperation{

    public srem( Value op1, Value op2){
        super(IntegerType.i32, op1, op2);
    }

    @Override
    public String ir() {
        return this.getName() + " = srem " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }
}
