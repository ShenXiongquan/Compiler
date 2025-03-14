package frontend.llvm_ir.instructions.ControlFlowInstructions;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.VoidType;

import java.util.ArrayList;

/**
 * ret <type> <value>
 * ret void
 */
public class ret extends ControlFlowInstr {
    /**
     * 返回值为void
     */
    public ret() {
        super(new VoidType(), new ArrayList<>());
    }

    /**
     * 返回值不为void
     */
    public ret(Value returnValue) {
        super(returnValue.getType(), new ArrayList<>() {{
            add(returnValue);
        }});
    }

    @Override
    public String ir() {
        if (getType() instanceof VoidType) {
            return "ret " + getType().ir();
        } else {
            return "ret " + getType().ir() + " " + getOperand(0).getName();
        }
    }

    public boolean isVoidType() {
        return getType().ir().equals("void");
    }
}
