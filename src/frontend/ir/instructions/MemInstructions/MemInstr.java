package frontend.ir.instructions.MemInstructions;

import frontend.ir.Function;
import frontend.ir.Value;
import frontend.ir.instructions.Instruction;
import frontend.ir.type.Type;
import frontend.ir.type.VoidType;

import java.util.ArrayList;

public abstract class MemInstr extends Instruction {

    protected Type valueType;
    /**
     * @param returnType 指令返回值类型
     * @param operands   指令的操作数
     */
    public MemInstr(Type returnType, ArrayList<Value> operands) {
        super(returnType instanceof VoidType?null:LOCAL_PREFIX+(Function.VarNum++), returnType, operands);
    }
}
