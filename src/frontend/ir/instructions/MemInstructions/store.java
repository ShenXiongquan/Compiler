package frontend.ir.instructions.MemInstructions;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.type.PointerType;
import frontend.ir.type.Type;
import frontend.ir.type.VoidType;

import java.util.ArrayList;

/**
 * 	store <ty> <value>, ptr <pointer>	写内存
 */
public class store extends MemInstr{
    public store(Value value, Value pointer, BasicBlock block) {
        super(new VoidType(), new ArrayList<>(){{add(value);add(pointer);}}, block);
    }

    @Override
    public String ir() {

        return "store "+getOperand(0).getType().ir()+" "+getOperand(0).getName()+", "+getOperand(1).getType().ir()+" "+getOperand(1).getName();
    }
}
