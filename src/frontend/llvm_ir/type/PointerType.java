package frontend.llvm_ir.type;

/**
 指针类型,可以指向:
 IntegerType
 ArrayType

 */
public class PointerType extends Type{

    private Type pointedType; // 指针指向的类型

    // 构造方法
    public PointerType(Type pointedType) {
        this.pointedType = pointedType;
    }

    // 获取指向的类型
    public Type getPointedType() {
        return pointedType;
    }

    @Override
    public int getByteSize() {
        return 4;
    }
    @Override
    public String ir() {
        return pointedType.ir()+"*";
    }
}
