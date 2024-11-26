package frontend.llvm_ir.type;

public class LabelType extends Type{
    @Override
    public int getByteSize() {
        throw new AssertionError("labelType has no size!");
    }

    @Override
    public String ir() {
        return "label";
    }
}
