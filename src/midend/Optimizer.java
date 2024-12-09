package midend;

import frontend.llvm_ir.*;
import frontend.llvm_ir.instructions.ControlFlowInstructions.ControlFlowInstr;
import frontend.llvm_ir.instructions.Instruction;
import frontend.llvm_ir.instructions.MemInstructions.store;
import frontend.llvm_ir.instructions.MixedInstructions.call;

import java.util.*;

/**
 * 优化器类，实现中端优化策略
 */
public class Optimizer {
    private final boolean enableOptimization;

    /**
     * 构造函数，设置是否启用优化
     *
     * @param enableOptimization 如果为 true，则启用优化
     */
    public Optimizer(boolean enableOptimization) {
        this.enableOptimization = enableOptimization;
    }

    /**
     * 执行所有优化
     */
    public void optimize(Model model) {
        if (!enableOptimization) {
            System.out.println("优化已禁用，跳过优化步骤。");
            return;
        }
        for (Function function : model.getFunctions()) {
            // 收集所有基本块中的指令
            List<Value> allValues = new ArrayList<>();
            for (BasicBlock basicBlock : function.getBasicBlocks()) {
                allValues.addAll(basicBlock.getInstructions());
            }
            // 执行公共子表达式消除（CSE）
//            performCSE(allValues);
            // 执行死代码消除（DCE）
            eliminateDeadCode(allValues);
        }
    }

    /**
     * 公共子表达式消除（Common Subexpression Elimination, CSE）
     *
     * @param allValues 所有 Value
     */
    private void performCSE(List<Value> allValues) {
        Map<String, Value> expressionMap = new HashMap<>();
        Iterator<Value> iterator = allValues.iterator();
        while (iterator.hasNext()) {
            Value value = iterator.next();
            if (value instanceof Instruction instr) {
                // 排除终止指令，不对其进行 CSE
                if (isJump(instr)) {
                    continue;
                }
                String key = generateCSEKey(instr);
                if (expressionMap.containsKey(key)) {
                    Value existing = expressionMap.get(key);
                    replaceUsers(instr, existing);
                    removeInstructionFromBasicBlocks(instr);
                    iterator.remove();
                } else {
                    expressionMap.put(key, value);
                }
            }
        }
    }

    /**
     * 生成公共子表达式消除的键
     *
     * @param instr 当前指令
     * @return 唯一的表达式键
     */
    private String generateCSEKey(Instruction instr) {
        StringBuilder sb = new StringBuilder();
        sb.append(instr.getClass().getSimpleName()).append("_");
        for (Value operand : instr.getOperands()) {
            sb.append(operand.getName()).append(",");
        }
        return sb.toString();
    }

    /**
     * 替换所有使用旧值的用户为新值
     *
     * @param oldValue 旧的 Value
     * @param newValue 新的 Value
     */
    private void replaceUsers(Value oldValue, Value newValue) {
        List<User> users = new ArrayList<>(oldValue.getUsers());
        for (User user : users) {
            user.replaceUse(oldValue, newValue);
        }
    }

    /**
     * 从所有基本块中移除指令
     *
     * @param instr 要移除的指令
     */
    private void removeInstructionFromBasicBlocks(Instruction instr) {
        Function function = instr.getParent().getParent();

        for (BasicBlock bb : function.getBasicBlocks()) {
            bb.removeInstruction(instr);
        }

    }

    /**
     * 死代码消除（Dead Code Elimination）
     *
     * @param allValues 所有 Value
     */
    private void eliminateDeadCode(List<Value> allValues) {
        Iterator<Value> iterator = allValues.iterator();
        while (iterator.hasNext()) {
            Value value = iterator.next();
            if (value.getUsers().isEmpty() && !isEssential(value)) {
                removeValue(value);
                iterator.remove();
            }
        }
    }

    /**
     * 判断一个 Value 是否是必要的（不能被删除）
     *
     * @param value 要检查的 Value
     * @return 如果是必要的，返回 true；否则返回 false
     */
    private boolean isEssential(Value value) {
        if (value instanceof GlobalValue || value instanceof store) {
            return true;
        }
        if (value instanceof Instruction instr) {
            return isJump(instr);
        }
        return false;
    }

    /**
     * 判断一条指令是否是跳转
     *
     * @param instr 要检查的指令
     * @return 如果是终止指令，返回 true；否则返回 false
     */
    private boolean isJump(Instruction instr) {
        return instr instanceof ControlFlowInstr || instr instanceof call;
    }

    /**
     * 从函数中移除一个 Value
     *
     * @param value 要移除的 Value
     */
    private void removeValue(Value value) {
        if (value instanceof Instruction instr) {
            // 从所属的基本块中移除指令
            BasicBlock parentBB = instr.getParent();
            if (parentBB != null) {
                parentBB.removeInstruction(instr);
            }
            // 移除该指令使用的操作数中的用户
            for (Value operand : instr.getOperands()) {
                operand.removeUser(instr);
            }
        }
    }
}
