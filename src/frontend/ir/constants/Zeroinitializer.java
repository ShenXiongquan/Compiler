package frontend.ir.constants;


import frontend.ir.type.ArrayType;

/**
 * 数组类型初始化为0
 */

public class Zeroinitializer extends Constant{
    private int length;

    public Zeroinitializer(ArrayType valueType) {
        super(valueType);
        this.length= valueType.getSize();
    }

    public int getLength() {
        return length;
    }


    @Override
    public String ir() {
        return "zeroinitializer";
    }


}
