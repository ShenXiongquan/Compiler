package frontend.ir.instructions.BinaryOperations;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.type.IntegerType;

/**
* <result> = sdiv <ty> <op1>, <op2>	有符号除法
 */
public class sdiv extends BinaryOperation{

    public sdiv( Value op1, Value op2, BasicBlock parent){
        super(new IntegerType(32), op1, op2, parent);
    }

    @Override
    public String ir() {
        return this.getName() + " = sdiv " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }
}
