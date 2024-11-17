package frontend.ir.instructions.ControlFlowInstructions;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.type.Type;

import java.util.ArrayList;

/**
 * br i1 <cond>, label <iftrue>, label <iffalse>
 * br label <dest>	改变控制流
 */
public class br extends ControlFlowInstr{

    public br(String name, Type valueType, BasicBlock block, ArrayList<Value> operands) {
        super(name, valueType, operands, block);
    }

    @Override
    public String ir() {
        return null;
    }
}
