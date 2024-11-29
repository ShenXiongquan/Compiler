package backend.operand;

public class PhysicalRegister {
    private final String name;
    private boolean isFree = true; // 初始状态为空闲

    public PhysicalRegister(String name) {
        this.name = name;
    }

    // 获取寄存器名称
    public String getName() {
        return name;
    }

    // 判断是否空闲
    public boolean isFree() {
        return isFree;
    }

    // 占用寄存器
    public void allocate() {
        if (!isFree) {
            throw new RuntimeException("Register already allocated: " + name);
        }
        isFree = false;
    }

    // 释放寄存器
    public void release() {
        isFree = true;
    }
}

