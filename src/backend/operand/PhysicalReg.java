package backend.operand;

import java.util.ArrayList;
import java.util.List;

public class PhysicalReg extends Reg {
    public static final PhysicalReg $zero = new PhysicalReg("zero");
    public static final PhysicalReg $v0 = new PhysicalReg("v0");
    public static final PhysicalReg $sp = new PhysicalReg("sp");
    public static final PhysicalReg $ra = new PhysicalReg("ra");
    public static final PhysicalReg $a0 = new PhysicalReg("a0");
    public static final PhysicalReg $a1 = new PhysicalReg("a1");
    public static final PhysicalReg $a2 = new PhysicalReg("a2");
    public static final PhysicalReg $a3 = new PhysicalReg("a3");
    public static final PhysicalReg $t0 = new PhysicalReg("t0");
    public static final PhysicalReg $t1 = new PhysicalReg("t1");
    public static final PhysicalReg $t2 = new PhysicalReg("t2");
    public static final PhysicalReg $t3 = new PhysicalReg("t3");
    public static final PhysicalReg $t4 = new PhysicalReg("t4");
    public static final PhysicalReg $t5 = new PhysicalReg("t5");
    public static final PhysicalReg $t6 = new PhysicalReg("t6");
    public static final PhysicalReg $t7 = new PhysicalReg("t7");
    public static final PhysicalReg $t8 = new PhysicalReg("t8");
    public static final PhysicalReg $t9 = new PhysicalReg("t9");
    public static final PhysicalReg $s0 = new PhysicalReg("s0");
    public static final PhysicalReg $s1 = new PhysicalReg("s1");
    public static final PhysicalReg $s2 = new PhysicalReg("s2");
    public static final PhysicalReg $s3 = new PhysicalReg("s3");
    public static final PhysicalReg $s4 = new PhysicalReg("s4");
    public static final PhysicalReg $s5 = new PhysicalReg("s5");
    public static final PhysicalReg $s6 = new PhysicalReg("s6");
    public static final PhysicalReg $s7 = new PhysicalReg("s7");


    public static final List<PhysicalReg> regs = new ArrayList<>() {{
        add($t0);
        add($t1);
        add($t2);
        add($t3);
        add($t4);
        add($t5);
        add($t6);
        add($t7);
        add($t8);
        add($t9);
        add($s0);
        add($s1);
        add($s2);
        add($s3);
        add($s4);
        add($s5);
        add($s6);
        add($s7);
    }};
    private boolean isFree = true; // 初始状态为空闲
    private VirtualReg associatedVirReg; // 关联的虚拟寄存器 (-1 表示无关联)

    public PhysicalReg(String name) {
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
        isFree = false;
//        associatedVirReg = virtualReg;
    }

    public void release() {
        isFree = true;
        associatedVirReg = null;
    }

    public VirtualReg getAssociatedVirReg() {
        return associatedVirReg;
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