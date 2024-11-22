package frontend.ir.instructions.BinaryOperations;

import frontend.ir.Value;
import frontend.ir.type.IntegerType;

/**
* <result> = sdiv <ty> <op1>, <op2>	有符号除法
 */
public class sdiv extends BinaryOperation{

    public sdiv( Value op1, Value op2){
        super(IntegerType.i32, op1, op2);
    }

    @Override
    public String ir() {
        return this.getName() + " = sdiv " + getType().ir() + " " + getOp1().getName() + ", " + getOp2().getName();
    }
}
