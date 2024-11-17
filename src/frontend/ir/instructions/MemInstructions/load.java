package frontend.ir.instructions.MemInstructions;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.type.PointerType;

import java.util.ArrayList;


/**
 * <result> = load <ty>, ptr <pointer>	读取内存
 */
public class load extends MemInstr{

    public load(Value pointer,BasicBlock block) {
        super(((PointerType)(pointer.getType())).getPointedType(), new ArrayList<>(){{add(pointer);}},block);
    }

    @Override
    public String ir() {
        return getName()+" = load "+getType().ir()+", "+getOperand(0).getType().ir()+" "+getOperand(0).getName();
    }
}
