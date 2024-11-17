package frontend.ir.instructions.BinaryOperations;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.type.IntegerType;

/**
 * or	<result> = or <ty> <op1>, <op2>	按位或
 */
public class or extends BinaryOperation{

    public or( Value op1, Value op2, BasicBlock parent){
        super(new IntegerType(32), op1, op2, parent);
    }

    @Override
    public String ir() {
        return this.getName() + " = or " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }
}
