package backend.MIPSInstructions;

import backend.operand.Operand;
import backend.operand.PhysicalRegister;

//beq/bne,j,jr,jal
public class MIPSJump extends MIPSInstruction {
    private final String type;

    // 构造方法，初始化目标地址
    public MIPSJump(String type, Operand targetAddr) {
        this.operand3 = targetAddr;
        this.type = type;
    }

    public MIPSJump(String type, Operand cond, Operand targetAddr) {
        this.operand1 = cond;
        this.operand2 = PhysicalRegister.$zero;
        this.operand3 = targetAddr;
        this.type = type;
    }

    // 获取目标地址
    public Operand getTargetAddr() {
        return operand3;
    }

    @Override
    public String mips() {
        if (operand1 != null) {
            return type + " " + operand1.mips() + ", " + operand2.mips() + ", " + getTargetAddr().mips();
        }
        return type + " " + getTargetAddr().mips();
    }
}
