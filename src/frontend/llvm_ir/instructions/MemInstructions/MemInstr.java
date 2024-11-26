package frontend.llvm_ir.instructions.MemInstructions;

import frontend.llvm_ir.Function;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.instructions.Instruction;
import frontend.llvm_ir.type.Type;
import frontend.llvm_ir.type.VoidType;

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
