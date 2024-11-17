package frontend.ir.type;

import java.util.ArrayList;
import java.util.List;

public class FunctionType extends Type{
    private Type returnType;           // 函数返回类型
    private ArrayList<Type> parameterList;  // 参数列表

    // 构造方法：用于函数类型
    public FunctionType(Type returnType, List<Type> parameterList) {

        if (returnType == null || parameterList == null) {
            throw new IllegalArgumentException("Return type and parameter list cannot be null");
        }
        this.returnType = returnType;
        this.parameterList = new ArrayList<>(parameterList);
    }

    // 获取返回类型
    public Type getReturnType() {
        return returnType;
    }

    // 获取参数列表
    public ArrayList<Type> getParameterList() {
        return parameterList;
    }

    public int getParmNum(){
        return parameterList.size();
    }

    @Override
    public int getSize() {
        throw new AssertionError(" functionType has no size!");
    }

    @Override
    public String ir() {
        return null;
    }
}
