package frontend.ir.constants;

import frontend.ir.type.IntegerType;

public class ConstInt extends Constant{

    public static ConstInt zero=new ConstInt(new IntegerType(32),0);

    public static ConstInt zeroI8=new ConstInt(new IntegerType(8),0);
    private int value;
    private int bitWidth;

    public ConstInt(IntegerType type, int value){
        super(type);
        this.bitWidth = type.getBitWidth();
        if(type.getBitWidth()==8) value&=0xFF;
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    @Override
    public String ir() {
        return String.valueOf(value);
    }

    @Override
    public String getName() {
        return ir();
    }
}
