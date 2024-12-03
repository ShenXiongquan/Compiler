package frontend.llvm_ir;

import frontend.llvm_ir.constants.Constant;
import frontend.llvm_ir.type.PointerType;

/**
 * 全局变量，全局数组，全局字符串，全局常量，树的第二层
 * // @.str = private unnamed_addr constant [4 x i8] c" - \00", align 1
 * // @.str.1 = private unnamed_addr constant [2 x i8] c"\0A\00", align 1
 */
public class GlobalVar extends GlobalValue {
    private static int num = 0;
    private boolean isConstant;     // 是否是常量
    private final Constant initial;

    private final boolean isPrintStr;

    /**
     * @param name       全局变量名
     * @param initial    变量的初值
     * @param isConstant 是否为常量
     */
    public GlobalVar(String name, Constant initial, boolean isConstant) {
        super(name, new PointerType(initial.getType()));
        this.isConstant = isConstant;
        this.initial = initial;
        this.isPrintStr = false;
    }

    public GlobalVar(Constant initial) {
        super(".str." + (num++), new PointerType(initial.getType()));
        this.initial = initial;
        this.isPrintStr = true;
    }

    public Constant getInitial() {
        return initial;
    }

    @Override
    public String ir() {
        if (isPrintStr)
            return getName() + " = private unnamed_addr constant " + ((PointerType) getType()).getPointedType().ir() + " " + initial.ir() + ", align 1";
        else
            return getName() + " = dso_local " + ((isConstant) ? "constant " : "global ") + ((PointerType) getType()).getPointedType().ir() + " " + initial.ir();
    }
}
