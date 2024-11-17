package frontend.ir.instructions.MemInstructions;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.instructions.Instruction;
import frontend.ir.type.Type;

import java.util.ArrayList;

public abstract class MemInstr extends Instruction {

    protected Type valueType;
    /**
     * @param returnType 指令返回值类型
     * @param operands   指令的操作数
     * @param block      指令的所在的块
     */
    public MemInstr(Type returnType, ArrayList<Value> operands, BasicBlock block) {
        super(Value.LOCAL_PREFIX+(Value.VarNum++), returnType, operands,block);
    }
}
