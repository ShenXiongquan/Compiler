package frontend.ir.instructions.BinaryOperations;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.type.IntegerType;

/**
 * <result> = or <ty> <op1>, <op2>
 */
public class mul extends BinaryOperation{

    public mul(Value op1, Value op2, BasicBlock parent){
        super(new IntegerType(32), op1, op2, parent);
    }

    @Override
    public String ir() {
        return this.getName() + " = mul " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }
}
