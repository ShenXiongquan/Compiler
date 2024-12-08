package backend.module;

import frontend.llvm_ir.constants.Constant;

/**
 * .data
 * a: .word 2
 * b: .word 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
 * c: .byte 0
 * d: .byte 0:10
 * str: .asciiz "This is a string\n"
 * 全局数组，保存在 .data 中，通过 la 加载其基地址。
 * 局部数组，在栈上分配一段连续的空间，通过 $sp 与偏移量访问。
 * 参数数组，传递数组基地址即可，保存时也只是保存该地址。
 */
public class MIPSGlobalVar {

    private final String name;
    private final Constant initialValue;

    public MIPSGlobalVar(String name, Constant initialValue) {
        this.name = name;
        this.initialValue = initialValue;
    }

    String mips() {
        return "\t" + name + ": " + initialValue.mips() + "\n";
    }
}
