package backend.operand;

import frontend.llvm_ir.type.IntegerType;

public class VirtualRegister extends Operand {
    private String name;       // 虚拟寄存器名称（如 %1）
    private IntegerType type;       // 数据类型（如 i32, i）
    private boolean allocated; // 是否已经被映射到实际寄存器或栈空间

    public VirtualRegister(String name, IntegerType type) {
        this.name = name;
        this.type = type;
        this.allocated = false;
    }

    // 获取寄存器名称
    public String getName() {
        return name;
    }

    public IntegerType getType() {
        return type;
    }

    // 是否已分配
    public boolean isAllocated() {
        return allocated;
    }

    // 设置分配状态
    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }

    @Override
    public String mips() {
        return null;
    }
}

