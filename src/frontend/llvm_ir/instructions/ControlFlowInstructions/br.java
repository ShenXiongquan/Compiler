package frontend.llvm_ir.instructions.ControlFlowInstructions;

import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.VoidType;

import java.util.ArrayList;

/**
 * br i1 <cond>, label <iftrue>, label <iffalse>
 * br label <dest>	用于改变控制流
 */
public class br extends ControlFlowInstr {

    private Value condition;       // 条件表达式

    /**
     * 无条件的跳转
     *
     * @param targetBlock 目标基本块
     */
    public br(BasicBlock targetBlock) {
        super(new VoidType(), new ArrayList<>() {{
            add(targetBlock);
        }});
    }

    /**
     * 有条件的跳转
     *
     * @param condition  条件表达式
     * @param trueBlock  条件为 true 时的目标块
     * @param falseBlock 条件为 false 时的目标块
     */
    public br(Value condition, BasicBlock trueBlock, BasicBlock falseBlock) {
        super(new VoidType(), new ArrayList<>() {{
            add(trueBlock);
            add(falseBlock);
        }});
        this.condition = condition;
        condition.addUser(this);
    }

    /**
     * 生成 LLVM IR 格式的字符串
     *
     * @return LLVM IR 表达式
     */
    @Override
    public String ir() {
        if (condition == null) {// 无条件跳转
            return "br label " + getOperand(0).getName();
        } else {// 有条件跳转
            return "br i1 " + condition.getName() + ", label " + getOperand(0).getName() + ", label " + getOperand(1).getName();
        }
    }

    /**
     * @return 是否为无条件跳转
     */
    public boolean isJmp() {
        return condition == null;
    }

    public String getTrueLabel() {
        return getOperand(0).getName().substring(1);
    }

    public String getLabel() {
        return getOperand(0).getName().substring(1);
    }

    public Value getCondition() {
        return condition;
    }

    public String getFalseLabel() {
        return getOperand(1).getName().substring(1);
    }
}
