package backend.MIPSInstructions;

import backend.operand.Operand;
import backend.operand.Reg;

import java.util.ArrayList;

public abstract class MIPSInstruction {
    protected Operand operand1;

    protected Operand operand2;

    protected Operand operand3;

    public abstract String mips();

    protected final ArrayList<Reg> regDef = new ArrayList<>();

    protected final ArrayList<Reg> regUse = new ArrayList<>();

    protected void addDef(Operand operand) {
        if (operand instanceof Reg reg) {
            regDef.add(reg);
        }
    }

    protected void addUse(Operand operand) {
        if (operand instanceof Reg reg) {
            regUse.add(reg);
        }
    }

    public ArrayList<Reg> getDefs() {
        return regDef;
    }

    public ArrayList<Reg> getUses() {
        return regUse;
    }

    public void replaceReg(Reg oldReg, Reg newReg) {
        for (int i = 0; i < regUse.size(); i++) {
            if (regUse.get(i).equals(oldReg)) {
                regUse.set(i, newReg);
            }
        }
        for (int i = 0; i < regDef.size(); i++) {
            if (regDef.get(i).equals(oldReg)) {
                regDef.set(i, newReg);
            }
        }
    }

}
