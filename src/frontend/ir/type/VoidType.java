package frontend.ir.type;

public class VoidType extends Type{
    @Override
    public int getSize() {
        throw new AssertionError("VoidType has no size!");
    }

    @Override
    public String ir() {
        return "void";
    }
}
