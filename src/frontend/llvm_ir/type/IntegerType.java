package frontend.llvm_ir.type;


/**
 * int a = 'a';
 * char b = 2 + 3;
 *
 * @a = dso_local global i32 97
 * @b = dso_local global i8 5
 */
public class IntegerType extends Type {

    public static final IntegerType i64 = new IntegerType(64);
    public static final IntegerType i32 = new IntegerType(32);

    public static final IntegerType i8 = new IntegerType(8);

    public static final IntegerType i1 = new IntegerType(1);

    private final int bitWidth;  // 用于表示整数的位宽，例如 32 表示 32 位整数

    // 构造方法
    public IntegerType(int bitWidth) {
        this.bitWidth = bitWidth;
    }

    @Override
    public boolean isInt32() {
        return bitWidth == 32;
    }

    @Override
    public boolean isInt8() {
        return bitWidth == 8;
    }

    public boolean isInt1() {
        return bitWidth == 1;
    }

    @Override
    public int getByteSize() {
        return bitWidth / 8;
    }

    @Override
    public String ir() {
        return "i" + bitWidth;
    }


}
