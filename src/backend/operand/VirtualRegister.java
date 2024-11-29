package backend.operand;

public class VirtualRegister {
    private String name;       // 虚拟寄存器名称（如 %1）
    private String type;       // 数据类型（如 i32, i64）
    private boolean allocated; // 是否已经被映射到实际寄存器或栈空间

    public VirtualRegister(String name, String type) {
        this.name = name;
        this.type = type;
        this.allocated = false;
    }

    // 获取寄存器名称
    public String getName() {
        return name;
    }

    // 获取数据类型
    public String getType() {
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

}

