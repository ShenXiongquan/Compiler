package backend.MIPSInstructions;

import backend.operand.Operand;
import backend.operand.PhysicalRegister;

public class MIPSLoad extends MIPSInstruction {


    // 构造函数，初始化目标寄存器和基址寄存器 + 偏移量
    public MIPSLoad(PhysicalRegister dst, Operand offset, Operand base) {
        this.operand1 = dst;
        this.operand2 = offset;
        this.operand3 = base;
    }

    public Operand getDst() {
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
        return "lw " + getDst().mips() + ", " + getOffset().mips() + "(" + getBase().mips() + ")";
    }
}
