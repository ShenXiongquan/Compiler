package backend;

import frontend.llvm_ir.Model;

public class Generator {
    private final Model irModel;

    public Generator(Model irModel) {
        this.irModel = irModel;
    }

    public String mips() {
        return null;
    }
}
