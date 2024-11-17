package frontend.ir.instructions.BinaryOperations;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.type.IntegerType;


/**
 * icmp	<result> = icmp <cond> <ty> <op1>, <op2>	比较指令
 */
public class icmp extends BinaryOperation{


    public icmp( Value op1, Value op2, BasicBlock parent) {
        super(new IntegerType(32), op1, op2, parent);
    }

    @Override
    public String ir() {
        return null;
    }
}
