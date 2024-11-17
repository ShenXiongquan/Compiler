package frontend.ir;

import frontend.ir.constants.Constant;
import frontend.ir.type.Type;

/**
 * 全局变量，全局数组，全局字符串，全局常量，树的第二层
 */
public class GlobalVariable extends GlobalValue{
    private boolean isConstant;     // 是否是常量

    private Constant initial;

    /**
     * @param name 全局变量名
     * @param type 变量类型
     * @param initial 变量的初值
     * @param isConstant 是否为常量
     */
    public GlobalVariable(String name, Type type, Constant initial,boolean isConstant) {
        super(name, type);
        this.isConstant = isConstant;
        this.initial=initial;
    }

    @Override
    public String ir() {
        return getName() + " = dso_local " + ((isConstant) ? "constant " : "global ") + getType().ir() + " " +initial.ir();
    }
}
