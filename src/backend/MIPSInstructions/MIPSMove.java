package backend.MIPSInstructions;

import backend.operand.Immediate;
import backend.operand.Label;
import backend.operand.Operand;
import backend.operand.Reg;

public class MIPSMove extends MIPSInstruction {


    /**
     * @param dst 目标寄存器
     * @param src 源寄存器或地址或者立即数
     */

    public MIPSMove(Operand dst, Operand src) {
        if (dst == null) throw new RuntimeException();
        this.operand1 = dst;
        this.operand3 = src;
        addDef(dst);
        addUse(src);

    }

    /**
     * @param dst    目标寄存器
     * @param offset 偏移量
     * @param base   基址寄存器
     */
    public MIPSMove(Operand dst, Operand offset, Operand base) {
        if (dst == null) throw new RuntimeException();
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

    public Operand getSrc() {
        return operand3;
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
        if (operand3.equals(oldReg)) {
            operand3 = newReg;
        }
        if (operand2 != null && operand2.equals(oldReg)) {
            operand2 = newReg;
        }
    }

    @Override
    public String mips() {
        if (getSrc() instanceof Label && getOffset() == null) {
            return "la " + getDst().mips() + ", " + getSrc().mips();
        } else if (getSrc() instanceof Label) {
            return "la " + getDst().mips() + ", " + getOffset().mips() + "(" + getBase().mips() + ")";
        } else if (getSrc() instanceof Immediate) {
            return "li " + getDst().mips() + ", " + getSrc().mips();
        } else {
            return "move " + getDst().mips() + ", " + getSrc().mips();
        }
    }
}
