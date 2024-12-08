package frontend.llvm_ir;

import frontend.llvm_ir.type.Type;

public class Parameter extends Value {
    private final String argName;


    public Parameter(String argName, Type valueType) {
        super(LOCAL_PREFIX + (Function.VarNum++), valueType);
        this.argName = argName;
    }

    public String getArgName() {
        return argName;
    }

    public int getParamIndex() {
        return Integer.parseInt(getName().substring(1));
    }

    @Override
    public String ir() {
        return getType().ir() + " " + getName();
    }
}
