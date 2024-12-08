package backend.MIPSInstructions;

import backend.operand.Operand;
import backend.operand.Reg;

public class MIPSLoad extends MIPSInstruction {


    // 构造函数，初始化目标寄存器和基址寄存器 + 偏移量

    /**
     * @param dst    目标寄存器
     * @param offset 偏移量
     * @param base   基地址寄存器
     */
    public MIPSLoad(Operand dst, Operand offset, Operand base) {
        this.operand1 = dst;
        this.operand2 = offset;
        this.operand3 = base;

        addDef(dst);
        addUse(base);
        addUse(offset);
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
    public void replaceReg(Reg oldReg, Reg newReg) {
        super.replaceReg(oldReg, newReg);

        // 更新操作数
        if (operand1.equals(oldReg)) {
            operand1 = newReg;
        }
        if (operand2.equals(oldReg)) {
            operand2 = newReg;
        }
        if (operand3.equals(oldReg)) {
            operand3 = newReg;
        }
    }

    @Override
    public String mips() {
        return "lw " + getDst().mips() + ", " + getOffset().mips() + "(" + getBase().mips() + ")";
    }


}
