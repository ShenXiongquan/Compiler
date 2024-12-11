package frontend.llvm_ir.instructions.MemInstructions;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.VoidType;

import java.util.ArrayList;

public class store2Stack extends MemInstr {

    private final int argIndex;

    private final int argsNum;

    public store2Stack(Value arg, int argIndex, int argsNum) {
        super(new VoidType(), new ArrayList<>() {{
            add(arg);
        }});
        this.argIndex = argIndex;
        this.argsNum = argsNum;
    }

    public int getArgsNum() {
        return argsNum;
    }

    public int getArgIndex() {
        return argIndex;
    }

    @Override
    public String ir() {
        return "";
    }
}
