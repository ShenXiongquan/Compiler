package frontend.ir.instructions.ControlFlowInstructions;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.type.Type;

import java.util.ArrayList;

/**
 * ret <type> <value>
 * ret void
 */
public class ret extends ControlFlowInstr{
    public ret( Type valueType, BasicBlock block, ArrayList<Value> operands) {
        super(null, valueType, operands, block);
    }

    @Override
    public String ir() {
        return null;
    }
}
