package backend;

import frontend.llvm_ir.Function;

import java.util.ArrayList;
import java.util.List;

//非叶函数要在序言处保存全局寄存器，在函数调用时保存临时寄存器
public class MIPSFunction {
    // 函数的名称
    private final String name;

    // 函数中的基本块列表
    private final List<MIPSBasicBlock> blocks;

    // 构造函数
    public MIPSFunction(String name) {
        this.name = name;
        this.blocks = new ArrayList<>();
    }

    public void addBasicBlock(MIPSBasicBlock block) {
        blocks.add(block);
    }

    public void buildFunction(Function function) {

    }

    public String getName() {
        return name;
    }

    public String mips() {
        StringBuilder sb = new StringBuilder();
        // 函数的起始标签
        sb.append(name).append(":\n");
        // 生成每个基本块的 MIPS 代码
        for (MIPSBasicBlock block : blocks) {
            sb.append(block.mips()).append("\n");
        }
        return sb.toString();
    }
}

