package frontend.llvm_ir.instructions.MixedInstructions;

import frontend.llvm_ir.Function;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.instructions.Instruction;
import frontend.llvm_ir.type.IntegerType;

import java.util.ArrayList;

/**
 * <result> = zext <ty> <value> to <ty2>	将 ty 的 value 的 type 扩充为 ty2（zero extend）
 */
public class zext extends Instruction {
    /**
     * 对于扩展，只有在涉及表达式的运算的时候，才可能需要扩展为i32
     * @param value 被扩展的数
     */
    public zext( Value value) {
        super(LOCAL_PREFIX + (Function.VarNum++), IntegerType.i32, new ArrayList<>(){{add(value);}});
    }

    @Override
    public String ir() {
        return getName()+" = zext "+getOperand(0).getType().ir()+" "+getOperand(0).getName()+" to "+getType().ir();
    }
}
