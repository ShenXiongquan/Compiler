package frontend.llvm_ir.constants;


import frontend.llvm_ir.type.ArrayType;

/**
 * 数组类型初始化为0
 */

public class Zeroinitializer extends Constant {
    private final int length;

    public Zeroinitializer(ArrayType arrayType) {
        super(arrayType);
        this.length = arrayType.getArraySize();
    }


    @Override
    public String ir() {
        return "zeroinitializer";
    }

    public String mips() {
        return "0:" + length;
    }
}
