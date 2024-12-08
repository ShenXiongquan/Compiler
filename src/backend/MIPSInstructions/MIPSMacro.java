package backend.MIPSInstructions;

public class MIPSMacro extends MIPSInstruction {
    private final String macro;

    public MIPSMacro(String externalFuncName) {
        this.macro = externalFuncName;
    }

    @Override
    public String mips() {
        return macro;
    }
}
