package backend.MIPSInstructions;

import backend.operand.Operand;

public class MIPSMove extends MIPSInstruction {

    // 构造函数，初始化目标寄存器和源寄存器
    public MIPSMove(Operand dst, Operand src) {
        this.operand1 = dst;
        this.operand2 = src;
    }

    public Operand getDst() {
        return operand1;
    }

    public Operand getSrc() {
        return operand2;
    }

    @Override
    public String mips() {
        return "move " + getDst().mips() + ", " + getSrc().mips();
    }
}
