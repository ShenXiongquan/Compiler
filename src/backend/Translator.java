package backend;

import backend.module.MIPSModel;
import frontend.llvm_ir.Model;

public class Translator {
    private final Model irModel;

    private final MIPSModel mipsModel = new MIPSModel();

    public Translator(Model irModel) {
        this.irModel = irModel;
    }

    public MIPSModel genMips() {
        mipsModel.buildModel(irModel);
        return mipsModel;
    }
}
