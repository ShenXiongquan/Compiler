package frontend.llvm_ir.instructions.MemInstructions;

import frontend.llvm_ir.type.PointerType;
import frontend.llvm_ir.type.Type;

import java.util.ArrayList;

/**
 * <result> = alloca <type>	分配内存,返回值是一个指针
 */
public class alloca extends MemInstr{


    /**
     * alloca返回值是指针类型
     */
    public alloca(Type valueType) {
        super(new PointerType(valueType),new ArrayList<>());
        this.valueType=valueType;
    }

    @Override
    public String ir() {
        return this.getName()+" = alloca "+this.valueType.ir();
    }
}
