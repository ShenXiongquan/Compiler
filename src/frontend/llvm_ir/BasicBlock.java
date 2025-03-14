package frontend.llvm_ir;

import frontend.llvm_ir.instructions.ControlFlowInstructions.ControlFlowInstr;
import frontend.llvm_ir.instructions.Instruction;
import frontend.llvm_ir.instructions.MemInstructions.loadFRStack;
import frontend.llvm_ir.instructions.MemInstructions.store2Stack;
import frontend.llvm_ir.type.LabelType;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示LLVM中的基本块，每个基本块由多个指令组成
 * 是中间代码树的第三层
 * 在LLVM IR中，基本块的区分方式基于控制流结构：
 * 入口指令：基本块通常从一个标签（label）开始，用于标记基本块的入口位置。
 * 结束指令：基本块的最后一条指令是一个“终结指令”（Terminator Instruction），如br（条件跳转或无条件跳转）或ret（返回），这些指令控制着程序的下一步执行流向。
 */
public class BasicBlock extends Value {

    /**
     * 基本块包含的指令
     */
    private final List<Instruction> instructions = new ArrayList<>();
    /**
     * 基本块的父作用域
     */
    private final Function parent;
    private final List<BasicBlock> predecessors;   // 前驱基本块列表
    private final List<BasicBlock> successors;     // 后继基本块列表

    private static int num = 0;

    // 构造函数
    public BasicBlock() {
        super(LOCAL_PREFIX + "block" + num++, new LabelType());
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
        this.parent = Visitor.curFunc;
    }

    /**
     * 添加一条指令到基本块中。
     */
    public void addInstruction(Instruction Instruction) {
        instructions.add(Instruction);
    }

    public void removeInstruction(Instruction instruction) {
        instructions.remove(instruction);
    }

    /**
     * 检查指令是否是终结指令。
     */
    public boolean isJumpInstruction(Instruction instruction) {
        return instruction instanceof ControlFlowInstr;
    }

    /**
     * @return 基本块的最后一条指令
     */
    public Instruction getLastInstruction() {
        return instructions.isEmpty() ? null : instructions.get(instructions.size() - 1);
    }

    /**
     * 判断基本块为空
     *
     * @return 为空返回true
     */
    public boolean isEmpty() {
        return this.instructions.isEmpty();
    }

    /**
     * 获取基本块的指令列表。
     */
    public List<Instruction> getInstructions() {
        return instructions;
    }

    /**
     * 添加一个前驱基本块。
     */
    public void addPredecessor(BasicBlock predecessor) {
        if (!predecessors.contains(predecessor)) {
            predecessors.add(predecessor);
        }
    }

    /**
     * 添加一个后继基本块。
     */
    public void addSuccessor(BasicBlock successor) {
        if (!successors.contains(successor)) {
            successors.add(successor);
        }
    }

    /**
     * 获取前驱基本块列表。
     */
    public List<BasicBlock> getPredecessors() {
        return predecessors;
    }

    /**
     * 获取后继基本块列表。
     */
    public List<BasicBlock> getSuccessors() {
        return successors;
    }

    /**
     * 获取父作用域（通常是一个函数）。
     */
    public Function getParent() {
        return parent;
    }

    /**
     * 生成LLVM IR的字符串表示。
     */
    @Override
    public String ir() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName().substring(1)).append(":");
        sb.append("\n");
        for (Instruction inst : instructions) {
            if (!(inst instanceof store2Stack || inst instanceof loadFRStack)) {
                sb.append("    ").append(inst.ir()).append("\n");
            }
        }

        return sb.toString();
    }
}
