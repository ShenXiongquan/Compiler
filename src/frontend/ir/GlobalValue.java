package frontend.ir;

import frontend.ir.type.FunctionType;
import frontend.ir.type.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个 GlobalValue 可以是全局变量（GlobalVariable），也可以是函数（Function）。是树的第二层的整体抽象
 */
public abstract class GlobalValue extends Value{

    public GlobalValue(String name, Type valueType) {
        super(Value.GLOBAL_PREFIX+name, valueType);
    }

    public abstract String ir();

}
