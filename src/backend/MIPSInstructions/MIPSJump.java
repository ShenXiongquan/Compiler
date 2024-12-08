package backend.MIPSInstructions;

import backend.operand.Operand;
import backend.operand.Reg;

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
        this.operand3 = targetAddr;
        this.type = type;
        addUse(cond);
    }

    @Override
    public void replaceReg(Reg oldReg, Reg newReg) {
        super.replaceReg(oldReg, newReg);

        // 更新操作数
        if (operand1 != null && operand1.equals(oldReg)) {
            operand1 = newReg;
        }
        if (operand2 != null && operand2.equals(oldReg)) {
            operand2 = newReg;
        }
        if (operand3 != null && operand3.equals(oldReg)) {
            operand3 = newReg;
        }
    }

    // 获取目标地址
    public Operand getTargetAddr() {
        return operand3;
    }


    @Override
    public String mips() {
        if (operand1 != null) {
            return type + " " + operand1.mips() + ", " + getTargetAddr().mips();
        }
        return type + " " + getTargetAddr().mips();
    }


}
