package frontend.llvm_ir.instructions.MemInstructions;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.ArrayType;
import frontend.llvm_ir.type.PointerType;

import java.util.ArrayList;

/**
 * <result> = getelementptr <ty>, ptr <ptrval>{, <ty> <idx>}*	计算目标元素的位置（数组部分会单独详细说明）
 */
public class getelementptr extends MemInstr{
    private final Value ptrval;

    /**
     * @param ptrval  初始指针
     * @param offset1 偏移量1
     */
    public getelementptr(Value ptrval,Value offset1) {
        super(ptrval.getType(), new ArrayList<>(){{add(offset1);}});
        this.ptrval=ptrval;
        this.valueType=((PointerType) ptrval.getType()).getPointedType();
    }

    /**
     * @param ptrval  初值指针
     * @param offset1 偏移量1
     * @param offset2 偏移量2
     */
    public getelementptr(Value ptrval,Value offset1,Value offset2) {
        super(new PointerType(((ArrayType)((PointerType)ptrval.getType()).getPointedType()).getElementType()),new ArrayList<>(){{add(offset1);add(offset2);}});
        this.ptrval=ptrval;
        this.valueType=((PointerType) ptrval.getType()).getPointedType();
    }
    @Override
    public String ir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName())
        .append(" = getelementptr inbounds ")
        .append(this.valueType.ir()) // 获取指针指向的类型
        .append(", ")
        .append(ptrval.getType().ir()) // 指针的类型
        .append(" ")
        .append(ptrval.getName()) // 指针变量的名称
        .append(", ")
        .append(getOperand(0).getType().ir()) // 偏移量1的类型
        .append(" ")
        .append(getOperand(0).getName()); // 偏移量1的值
        // 如果有第二个偏移量，追加到指令中
        if (getOperands().size() > 1) {
            sb.append(", ")
                    .append(getOperand(1).getType().ir()) // 偏移量2的类型
                    .append(" ")
                    .append(getOperand(1).getName()); // 偏移量2的值
        }

        return sb.toString();
    }
}
