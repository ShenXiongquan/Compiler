package backend.operand;

public class VirtualReg extends Reg {
    private static int vNum = 0;
    private boolean allocated = false; // 是否已经被映射到实际寄存器或栈空间

    public VirtualReg() {
        this.name = "vr" + vNum++;
    }


    // 获取寄存器名称
    public String getName() {
        return name;
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
        return name;
    }

    /**
     * @return 虚拟寄存器的个数
     */
    public static int getVNum() {
        return vNum;
    }
}

