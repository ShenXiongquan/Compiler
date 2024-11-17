package frontend.ir.instructions.BinaryOperations;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.type.IntegerType;

/**
 * <result> = add <ty> <op1>, <op2>
 */
public class add extends BinaryOperation{
    public add( Value op1, Value op2, BasicBlock parent){
        super(new IntegerType(32), op1, op2, parent);
    }

    @Override
    public String ir() {
        return this.getName() + " = add " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }
}
