package frontend.llvm_ir.type;

public class VoidType extends Type{
    @Override
    public int getByteSize() {
        throw new AssertionError("VoidType has no size!");
    }

    @Override
    public String ir() {
        return "void";
    }

}
