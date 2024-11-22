package frontend.ir.instructions.MixedInstructions;

import frontend.ir.Function;
import frontend.ir.Value;
import frontend.ir.instructions.Instruction;
import frontend.ir.type.IntegerType;
import frontend.ir.type.Type;

import java.util.ArrayList;

/**
 * <result> = trunc <ty> <value> to <ty2>	将 ty 的 value 的 type 缩减为 ty2（truncate）
 */
public class trunc extends Instruction {
    /**
     * 对于截断指令，只有在涉及store存储的时候，可能要考虑截断
     * @param returnType 截断到的位数
     * @param value      需要截断的value
     */
    public trunc(Type returnType, Value value) {
        super(LOCAL_PREFIX + (Function.VarNum++), returnType, new ArrayList<>(){{add(value);}});
    }
    public trunc( Value value) {
        super(LOCAL_PREFIX + (Function.VarNum++), IntegerType.i8, new ArrayList<>(){{add(value);}});
    }

    @Override
    public String ir() {
        return getName()+" = trunc "+getOperand(0).getType().ir()+" "+getOperand(0).getName()+" to "+getType().ir();
    }
}
