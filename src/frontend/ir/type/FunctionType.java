package frontend.ir.type;



public class FunctionType extends Type{
    private Type returnType;           // 函数返回类型

    // 构造方法：用于函数类型
    public FunctionType(Type returnType) {

        this.returnType = returnType;
    }

    // 获取返回类型
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public int getSize() {
        throw new AssertionError(" functionType has no size!");
    }
    @Override
    public String ir() {
        throw new AssertionError(" functionType has no ir!");
    }
}
