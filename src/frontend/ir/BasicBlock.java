package frontend.ir;

import frontend.ir.type.Type;
import java.util.ArrayList;
import java.util.List;
import frontend.ir.instructions.Instruction;
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
    private List<Instruction> Instructions;
    /**
     * 基本块的父作用域
     */
    private Value parent;
    private List<BasicBlock> predecessors;   // 前驱基本块列表
    private List<BasicBlock> successors;     // 后继基本块列表


    // 构造函数
    public BasicBlock(String name, Type valueType, Value parent) {
        super(name, valueType);
        this.Instructions = new ArrayList<>();
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
        this.parent = parent;
    }

    /**
     * 添加一条指令到基本块中。
     */
    public void addInstruction(Instruction Instruction) {
        if (isTerminatorPresent()) {
            throw new IllegalStateException("Cannot add more Instructions after a terminator.");
        }
        Instructions.add(Instruction);
    }

    /**
     * 检查最后一条指令是否是终结指令。
     */
    public boolean isTerminatorPresent() {
        return true;
    }

    /**
     * 获取基本块的指令列表。
     */
    public List<Instruction> getInstructions() {
        return Instructions;
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
    public Value getParent() {
        return parent;
    }

    /**
     * 生成LLVM IR的字符串表示。
     */
    @Override
    public String ir() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName()).append(":").append("\n");
        for (Instruction inst : Instructions) {
            sb.append("  ").append(inst.ir()).append("\n");
        }
        return sb.toString();
    }
}
