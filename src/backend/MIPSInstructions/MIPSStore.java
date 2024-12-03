package backend.MIPSInstructions;

import backend.operand.Operand;

public class MIPSStore extends MIPSInstruction {

    // 构造函数，初始化源寄存器、偏移量和基址寄存器
    public MIPSStore(Operand src, Operand offset, Operand base) {
        this.operand1 = src;
        this.operand2 = offset;
        this.operand3 = base;
    }

    public Operand getSrc() {
        return operand1;
    }

    public Operand getOffset() {
        return operand2;
    }

    public Operand getBase() {
        return operand3;
    }

    @Override
    public String mips() {
        // 生成 sw 指令的 MIPS 汇编代码
        return "sw " + getSrc().mips() + ", " + getOffset().mips() + "(" + getBase().mips() + ")";
    }
}
