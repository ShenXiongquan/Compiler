package frontend.ir.instructions.BinaryOperations;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.type.IntegerType;

/**
 * <result> = sub <ty> <op1>, <op2>
 */
public class sub extends BinaryOperation{
    public sub(Value op1, Value op2, BasicBlock parent){
        super(new IntegerType(32), op1, op2, parent);
    }

    @Override
    public String ir() {
        return this.getName() + " = sub " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }
}
