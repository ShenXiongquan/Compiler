package backend;

import backend.operand.PhysicalRegister;

import java.util.HashMap;

//$zero：始终为 0，可以用来减少常数 0 的使用。
//$at（assembly temporary)：汇编器保留寄存器，由汇编器在特定场景（通常是加载大常数）自动生成。
//$v0 - $v1：作为函数返回值，一般返回值只使用 $v0，当返回值超过 32 位时需要同时使用 $v1。
//$a0 - $a3：函数调用参数，前 4 个参数通常保存在这几寄存器中，更多的参数则是压栈处理。
//$t0 - $t7, $t8 - $t9：临时寄存器，用于基本块内的变量，发生函数调用时不必保存。
//$s0 - $s7（saved）：全局寄存器，这些寄存器用于跨基本块的变量，往往需要在发生函数调用时进行保存。
//$k0 - $k1（keep）：系统保留寄存器，在系统发生中断时使用。
//$sp, $fp：栈帧寄存器，$sp 保存栈顶（低地址），$fp 保存栈底（高地址）。
//$ra：返回地址，jal 指令会自动将下一条指令的地址保存在 $ra 中，从而函数调用可以正确地返回。

/**
 * 对于规范的寄存器分配，则需要考虑全局寄存器和局部寄存器的分配，分别对应 MIPS 中的 s 和 t 寄存器。全局寄存器对应那些生命周期跨越基本块的变量，而局部寄存器则对应基本块内的变量。
 * 对于全局寄存器分配，我们需要考虑不同变量的生命周期范围，尽可能的避免寄存器冲突。
 * 为了解决这一问题，主要由图着色和线性两种分配方式。而对于局部寄存器分配，由于基本块内部不存在分支，结构较为简单，因此使用寄存器池就可以实现高效的寄存器分配。
 */
public class RegisterAllocator {
    private final HashMap<Integer, PhysicalRegister> registerPool = new HashMap<>();

    public RegisterAllocator() {
        addRegister(0, PhysicalRegister.$zero); // 常量零寄存器
        addRegister(2, PhysicalRegister.$v0);   // 返回值寄存器
        addRegister(29, PhysicalRegister.$sp);  // 栈顶指针寄存器
        addRegister(31, PhysicalRegister.$ra);  // 返回地址寄存器
        // 参数寄存器 a0-a3 (编号 4-7)
        for (int i = 0; i < 4; i++) addRegister(4 + i, new PhysicalRegister("a" + i));
        // 临时寄存器 t0-t7 (编号 8-15)
        for (int i = 0; i < 8; i++) addRegister(8 + i, new PhysicalRegister("t" + i));
        // (保存)全局寄存器 s0-s7 (编号 16-23)
        for (int i = 0; i < 8; i++) addRegister(16 + i, new PhysicalRegister("s" + i));
    }

    // 添加寄存器到池中
    private void addRegister(int index, PhysicalRegister register) {
        registerPool.put(index, register);
    }

    // 获取寄存器
    public PhysicalRegister getRegister(int index) {
        if (!registerPool.containsKey(index)) {
            throw new RuntimeException("Invalid register index: " + index);
        }
        return registerPool.get(index);
    }

    // 分配空闲寄存器
    public PhysicalRegister allocateRegister(String typePrefix) {
        for (PhysicalRegister register : registerPool.values()) {
            if (register.isFree() && register.getName().startsWith(typePrefix)) {
                register.allocate();
                return register;
            }
        }
        throw new RuntimeException("No free register available for type: " + typePrefix);
    }

    // 释放寄存器
    public void releaseRegister(int index) {
        PhysicalRegister register = getRegister(index);
        register.release();
    }

    // 打印所有寄存器状态（用于调试）
    public void printRegisterStatus() {
        registerPool.values().forEach(register ->
                System.out.println(register.getName() + " is " + (register.isFree() ? "free" : "allocated"))
        );
    }
}
