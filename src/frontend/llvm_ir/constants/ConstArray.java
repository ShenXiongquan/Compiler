package frontend.llvm_ir.constants;


import frontend.llvm_ir.type.ArrayType;

import java.util.ArrayList;
import java.util.List;

public class ConstArray extends Constant {
    /**
     * 常量数组的初始化
     */
    private final List<ConstInt> ArrayElements = new ArrayList<>();

    public ConstArray(ArrayType arrayType, ArrayList<ConstInt> ArrayElements) {
        super(arrayType);
        this.ArrayElements.addAll(ArrayElements);
    }

    public ConstInt getArrayElement(int index) {
        return ArrayElements.get(index);
    }

    @Override
    public String ir() {
        StringBuilder sb = new StringBuilder("[");
        int len = ((ArrayType) getType()).getArraySize();
        for (int i = 0; i < len; i++) {
            sb.append(getArrayElement(i).getType().ir()).append(" ").append(getArrayElement(i).getName());
            if (i < len - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public String mips() {
        StringBuilder sb = new StringBuilder();
        sb.append(((ArrayType) getType()).getElementType().getByteSize() == 4 ? ".word " : ".byte ");
        int len = ((ArrayType) getType()).getArraySize();
        for (int i = 0; i < len; i++) {
            sb.append(getArrayElement(i).getName());
            if (i < len - 1) sb.append(", ");
        }
        return sb.toString();
    }
}
