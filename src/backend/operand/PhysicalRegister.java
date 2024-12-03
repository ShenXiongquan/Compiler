package backend.operand;

public class PhysicalRegister extends Operand {
    public static final PhysicalRegister $zero = new PhysicalRegister("zero");
    public static final PhysicalRegister $v0 = new PhysicalRegister("v0");
    public static final PhysicalRegister $sp = new PhysicalRegister("sp");
    public static final PhysicalRegister $ra = new PhysicalRegister("ra");

    private boolean isFree = true; // 初始状态为空闲

    public PhysicalRegister(String name) {
        this.name = name;
    }

    // 判断是否空闲
    public boolean isFree() {
        return isFree;
    }

    public String getName() {
        return name;
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

    @Override
    public String mips() {
        return "$" + name;
    }
}

//    // 参数寄存器 a0-a3 (编号 4-7)
//    public static final PhysicalRegister $a0 = new PhysicalRegister("a0");
//    public static final PhysicalRegister $a1 = new PhysicalRegister("a1");
//    public static final PhysicalRegister $a2 = new PhysicalRegister("a2");
//    public static final PhysicalRegister $a3 = new PhysicalRegister("a3");
//    // 临时寄存器 t0-t7 (编号 8-15)
//    public static final PhysicalRegister $t0 = new PhysicalRegister("t0");
//    public static final PhysicalRegister $t1 = new PhysicalRegister("t1");
//    public static final PhysicalRegister $t2 = new PhysicalRegister("t2");
//    public static final PhysicalRegister $t3 = new PhysicalRegister("t3");
//    public static final PhysicalRegister $t4 = new PhysicalRegister("t4");
//    public static final PhysicalRegister $t5 = new PhysicalRegister("t5");
//    public static final PhysicalRegister $t6 = new PhysicalRegister("t6");
//    public static final PhysicalRegister $t7 = new PhysicalRegister("t7");
//    // (保存)全局寄存器 s0-s7 (编号 16-23)
//    public static final PhysicalRegister $s0 = new PhysicalRegister("s0");
//    public static final PhysicalRegister $s1 = new PhysicalRegister("s1");
//    public static final PhysicalRegister $s2 = new PhysicalRegister("s2");
//    public static final PhysicalRegister $s3 = new PhysicalRegister("s3");
//    public static final PhysicalRegister $s4 = new PhysicalRegister("s4");
//    public static final PhysicalRegister $s5 = new PhysicalRegister("s5");
//    public static final PhysicalRegister $s6 = new PhysicalRegister("s6");
//    public static final PhysicalRegister $s7 = new PhysicalRegister("s7");