package frontend.ir.type;


/**
 * int a = 'a';
 * char b = 2 + 3;
 * @a = dso_local global i32 97
 * @b = dso_local global i8 5
 */
public class IntegerType extends Type {

    public static  IntegerType i32=new IntegerType(32);

    public static IntegerType i8 =new IntegerType(8);

    private int bitWidth;  // 用于表示整数的位宽，例如 32 表示 32 位整数

    // 构造方法
    public IntegerType(int bitWidth) {
        this.bitWidth = bitWidth;
    }


    @Override
    public int getSize() {
        return bitWidth/8;
    }
    // 获取位宽
    public int getBitWidth() {
        return bitWidth;
    }

    @Override
    public String ir() {
        return "i"+bitWidth;
    }
}
