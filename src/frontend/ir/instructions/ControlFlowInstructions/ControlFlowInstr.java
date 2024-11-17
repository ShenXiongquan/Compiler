package frontend.ir.instructions.ControlFlowInstructions;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.instructions.Instruction;
import frontend.ir.type.Type;

import java.util.ArrayList;

public abstract class ControlFlowInstr extends Instruction {
    /**
     * @param name 指令的返回值
     * @param returnType 指令返回值类型
     * @param operands 指令的操作数
     * @param block 指令的所在的块
     */
    public ControlFlowInstr(String name, Type returnType, ArrayList<Value> operands, BasicBlock block) {
        super(name, returnType, operands, block);
    }
}
