package frontend.llvm_ir.instructions.MixedInstructions;

import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.instructions.Instruction;
import frontend.llvm_ir.type.Type;

import java.util.ArrayList;

/**
 * <result> = phi [fast-math-flags] <ty> [<val0>, <label0>], ...
 */
public class phi extends Instruction {
    /**
     * @param valueType 指令返回值类型
     * @param operands  指令的操作数
     * @param block     指令的所在的块
     */
    public phi(Type valueType, ArrayList<Value> operands, BasicBlock block) {
        super(LOCAL_PREFIX + (Function.VarNum++), valueType, operands);
    }

    @Override
    public String ir() {
        return null;
    }
}
