package frontend.llvm_ir.constants;


import frontend.llvm_ir.type.ArrayType;

/**
 * 数组类型初始化为0
 */

public class Zeroinitializer extends Constant{
    private int length;

    public Zeroinitializer(ArrayType arrayType) {
        super(arrayType);
        this.length= arrayType.getByteSize();
    }


    @Override
    public String ir() {
        return "zeroinitializer";
    }


}
