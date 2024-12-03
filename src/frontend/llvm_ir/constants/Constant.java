package frontend.llvm_ir.constants;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.Type;

/**
 * 定义：Constants类是Value类的子类，表示编译时已知的值，如整型常量、字符串等。Constants 是 User 类的子类，因为常量表达式（如a + 1）可以使用其他 Value。
 * 角色：Constants类不仅包括简单的常量，还包括由这些常量构成的复合常量，如 ConstantArray
 */

public abstract class Constant extends Value {
    /**
     * @param valueType 常量类型
     */
    public Constant(Type valueType) {
        super(null, valueType);
    }

    public String getName() {
        return ir();
    }

    public abstract String mips();
}
