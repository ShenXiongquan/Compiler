package backend.MIPSInstructions;

import backend.operand.Operand;

public abstract class MIPSInstruction {

    protected Operand operand1;

    protected Operand operand2;

    protected Operand operand3;


    public abstract String mips();

}
