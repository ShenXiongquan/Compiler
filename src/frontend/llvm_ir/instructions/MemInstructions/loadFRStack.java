package frontend.llvm_ir.instructions.MemInstructions;

import frontend.llvm_ir.Parameter;

import java.util.ArrayList;

public class loadFRStack extends MemInstr {

    private final Parameter parameter;

    public loadFRStack(Parameter parameter) {
        super(parameter.getType(), new ArrayList<>());
        this.parameter = parameter;
    }

    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public String ir() {
        return "";
    }
}
