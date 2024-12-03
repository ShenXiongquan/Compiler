package backend;

import frontend.llvm_ir.Model;

public class MIPSGenerator {
    private final Model irModel;
    private final RegisterAllocator registerAllocator = new RegisterAllocator();

    private MIPSModel mipsModel;

    public MIPSGenerator(Model irModel) {
        this.irModel = irModel;
    }

    public void genMips() {


    }
}
