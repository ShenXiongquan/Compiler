package frontend.ir.instructions.BinaryOperations;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.type.IntegerType;

/**
 *and	<result> = and <ty> <op1>, <op2>	按位与
 */
public class and extends BinaryOperation{

    public and( Value op1, Value op2, BasicBlock parent){
        super(new IntegerType(32), op1, op2, parent);
    }

    @Override
    public String ir() {
        return this.getName() + " = and " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }
}
