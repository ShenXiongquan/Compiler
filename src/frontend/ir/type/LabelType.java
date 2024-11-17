package frontend.ir.type;

public class LabelType extends Type{
    @Override
    public int getSize() {
        throw new AssertionError("labelType has no size!");
    }

    @Override
    public String ir() {
        return "label";
    }
}
