package frontend.ir.instructions.MemInstructions;

import frontend.ir.BasicBlock;
import frontend.ir.type.PointerType;
import frontend.ir.type.Type;

/**
 * <result> = alloca <type>	分配内存,返回值是一个指针
 */
public class alloca extends MemInstr{


    /**
     * alloca返回值是指令类型
     */
    public alloca(Type valueType, BasicBlock block) {
        super(new PointerType(valueType),null, block);
        this.valueType=valueType;
    }

    @Override
    public String ir() {
        return this.getName()+" = alloca "+this.valueType.ir();
    }
}
