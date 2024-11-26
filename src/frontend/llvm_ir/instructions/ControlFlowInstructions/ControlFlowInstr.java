package frontend.llvm_ir.instructions.ControlFlowInstructions;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.instructions.Instruction;
import frontend.llvm_ir.type.Type;

import java.util.ArrayList;

public abstract class ControlFlowInstr extends Instruction {
    /**
     * @param returnType 指令返回值类型
     * @param operands   指令的操作数
     */
    public ControlFlowInstr(Type returnType, ArrayList<Value> operands) {
        super(null, returnType, operands);
    }
}
