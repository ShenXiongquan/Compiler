package backend.MIPSInstructions;

import backend.operand.Operand;
import backend.operand.Reg;

public class MIPSBinary extends MIPSInstruction {

    public final String type;

    public MIPSBinary(String type, Operand dst, Operand src1, Operand src2) {
        this.operand1 = dst;
        this.operand2 = src1;
        this.operand3 = src2;
        this.type = type;
        addDef(dst);
        addUse(src1);
        addUse(src2);
    }

    public Operand getDst() {
        return operand1;
    }

    public Operand getSrc1() {
        return operand2;
    }

    public Operand getSrc2() {
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

        return type + " " +
                getDst().mips() + ", " +
                getSrc1().mips() + ", " +
                getSrc2().mips();
    }


}
