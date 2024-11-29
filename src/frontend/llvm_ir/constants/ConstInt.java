package frontend.llvm_ir.constants;

import frontend.llvm_ir.type.IntegerType;

public class ConstInt extends Constant{

    public static final ConstInt zero=new ConstInt(IntegerType.i32,0);

    public static final ConstInt zeroI8=new ConstInt(IntegerType.i8,0);

    public static final ConstInt zeroI64=new ConstInt(IntegerType.i64,0);
    private final int value;

    public ConstInt(IntegerType type, int value){
        super(type);
        if(type.getBitWidth()==8) value&=0xFF;
        this.value = value;
    }

    public boolean isZero(){
        return this.value==0;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String ir() {
        return String.valueOf(value);
    }


}
