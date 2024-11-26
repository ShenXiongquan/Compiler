package frontend.llvm_ir.instructions.BinaryOperations;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.IntegerType;


/**
 * icmp	<result> = icmp <cond> <ty> <op1>, <op2>	比较指令
 */
public class icmp extends BinaryOperation {

    public final static String EQ = "eq";  // Equal
    public final static String NE = "ne";  // Not Equal
    public final static String GE = "sge"; // Signed Greater Than or Equal
    public final static String GT = "sgt"; // Signed Greater Than
    public final static String LE = "sle"; // Signed Less Than or Equal
    public final static String LT = "slt"; // Signed Less Than

    private final String condition;

    /**
     *
     * @param condition 条件类型
     * @param op1 第一个数
     * @param op2 第二个数
     */
    public icmp(String condition, Value op1, Value op2) {
        super(IntegerType.i1, op1, op2);
        this.condition = condition;
    }

    @Override
    public String ir() {
        return getName() + " = icmp " + condition + " " + getOperand(0).getType().ir() + " " + getOperand(0).getName() + ", " + getOperand(1).getName();
    }
}
