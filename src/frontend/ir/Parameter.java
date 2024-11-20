package frontend.ir;

import frontend.ir.type.Type;

public class Parameter extends Value {
    private String argName;

    public Parameter(String argName, Type valueType) {
        super(LOCAL_PREFIX + (Function.VarNum++), valueType);
        this.argName=argName;
    }

    public String getArgName() {
        return argName;
    }

    @Override
    public String ir() {
        return getType().ir() + " " + getName();
    }
}
