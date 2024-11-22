package frontend.ir.instructions.ControlFlowInstructions;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.type.Type;
import frontend.ir.type.VoidType;

import java.util.ArrayList;

/**
 * br i1 <cond>, label <iftrue>, label <iffalse>
 * br label <dest>	用于改变控制流
 */
public class br extends ControlFlowInstr {

    private Value condition;       // 条件表达式

    /**
     * 无条件的跳转
     * @param targetBlock 目标基本块
     */
    public br(BasicBlock targetBlock) {
        super(new VoidType(), new ArrayList<>() {{
            add(targetBlock);
        }});
    }

    /**
     * 有条件的跳转
     * @param condition 条件表达式
     * @param trueBlock 条件为 true 时的目标块
     * @param falseBlock 条件为 false 时的目标块
     */
    public br(Value condition, BasicBlock trueBlock, BasicBlock falseBlock) {
        super(new VoidType(), new ArrayList<>() {{
            add(trueBlock);
            add(falseBlock);
        }});
        this.condition = condition;
    }

    /**
     * 生成 LLVM IR 格式的字符串
     * @return LLVM IR 表达式
     */
    @Override
    public String ir() {
        if (condition == null) {
            // 无条件跳转
            return "br label " + getOperand(0).getName();
        } else {
            // 有条件跳转
            return "br i1 " + condition.getName() + ", label " + getOperand(0).getName() + ", label " + getOperand(1).getName();
        }
    }

}
