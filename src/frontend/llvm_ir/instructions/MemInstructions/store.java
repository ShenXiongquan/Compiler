package frontend.llvm_ir.instructions.MemInstructions;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.VoidType;

import java.util.ArrayList;

/**
 * 	store <ty> <value>, ptr <pointer>	写内存
 */
public class store extends MemInstr{
    /**
     * @param storedValue 要存储的值
     * @param pointer     存储的地址
     */
    public store(Value storedValue, Value pointer) {
        super(new VoidType(), new ArrayList<>(){{add(storedValue);add(pointer);}});
    }

    @Override
    public String ir() {

        return "store "+getOperand(0).getType().ir()+" "+getOperand(0).getName()+", "+getOperand(1).getType().ir()+" "+getOperand(1).getName();
    }
}
