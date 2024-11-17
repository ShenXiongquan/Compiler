package frontend.ir.type;

/**
 * int a[1 + 2 + 3 + 4] = { 1, 1 + 1, 1 + 3 - 1, 0, 0, 0, 0, 0, 0, 0 };
 * int b[20];
 * char c[8] = "foobar";
 * @a = dso_local global [10 x i32] [i32 1, i32 2, i32 3, i32 0, i32 0, i32 0, i32 0, i32 0, i32 0, i32 0]
 * @b = dso_local global [20 x i32] zeroinitializer
 * @c = dso_local global [8 x i8] c"foobar\00\00", align 1
 */
public class ArrayType extends Type{
    private Type elementType; // 数组的元素类型
    private int size;
    private int elementNum;

    // 构造方法
    public ArrayType(Type elementType, int elementNum) {
        this.elementType = elementType;
        this.elementNum =elementNum;
        this.size = elementNum*elementType.getSize();
    }

    // 获取元素类型
    public Type getElementType() {
        return elementType;
    }

    // 获取数组大小
    public int getSize() {
        return size;
    }

    public int getElementNum() {
        return elementNum;
    }

    @Override
    public String ir() {
        return "[" + elementNum + " x " + elementType.ir() + "]";
    }
}
