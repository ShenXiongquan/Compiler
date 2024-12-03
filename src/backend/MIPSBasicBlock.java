package backend;

import backend.MIPSInstructions.MIPSInstruction;

import java.util.ArrayList;
import java.util.List;

public class MIPSBasicBlock {

    // 基本块的标签
    private String label;
    // 存储 MIPS 指令的列表
    private final List<MIPSInstruction> instructions;

    // 构造函数
    public MIPSBasicBlock(String label) {
        this.label = label;
        this.instructions = new ArrayList<>();
    }

    // 添加一条 MIPS 指令
    public void addInstruction(MIPSInstruction instruction) {
        instructions.add(instruction);
    }

    // 获取基本块的标签
    public String getLabel() {
        return label;
    }

    // 设置基本块的标签
    public void setLabel(String label) {
        this.label = label;
    }

    // 生成基本块的 MIPS 代码
    public String mips() {
        StringBuilder sb = new StringBuilder();

        // 添加基本块标签
        sb.append(label).append(":\n");

        // 添加每条指令
        for (MIPSInstruction instruction : instructions) {
            sb.append("\t").append(instruction.mips()).append("\n");
        }

        return sb.toString();
    }
}
