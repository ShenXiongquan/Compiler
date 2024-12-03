package frontend.llvm_ir.type;

public class LabelType extends Type {
    @Override
    public int getByteSize() {
        return -1;
    }

    @Override
    public String ir() {
        return "label";
    }
}
