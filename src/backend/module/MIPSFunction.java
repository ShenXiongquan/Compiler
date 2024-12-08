package backend.module;

import backend.MIPSInstructions.MIPSBinary;
import backend.operand.Immediate;
import backend.operand.PhysicalReg;
import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;

import java.util.LinkedList;

//非叶函数要在序言处保存全局寄存器，在函数调用时保存临时寄存器
public class MIPSFunction {
    // 函数的名称
    private final String name;

    // 函数中的基本块列表
    private final LinkedList<MIPSBasicBlock> blocks = new LinkedList<>();
    /**
     * 栈顶位置
     */
    public int stackTop;

    private final Function function;


    // 构造函数
    public MIPSFunction(String name, Function function) {
        this.name = name;
        this.function = function;
        stackTop = 0;
        MIPSModel.getValue2Stack().clear();
        MIPSModel.getValue2VReg().clear();
    }


    public void addBasicBlock(MIPSBasicBlock block) {
        blocks.add(block);
    }

    public void buildFunction() {
        for (BasicBlock basicBlock : function.getBasicBlocks()) {
            MIPSBasicBlock mipsBasicBlock = new MIPSBasicBlock(basicBlock.getName().substring(1), function, this);
            addBasicBlock(mipsBasicBlock);
            mipsBasicBlock.buildBasicBlock(basicBlock);
        }
        //在函数序言处分配栈空间和存储栈指针
        if (stackTop != 0) {
            MIPSBinary binary = new MIPSBinary("addiu", PhysicalReg.$sp, PhysicalReg.$sp, new Immediate(-stackTop));
            blocks.getFirst().getInstructions().addFirst(binary);
        }
    }

    /**
     * 获取函数名
     *
     * @return
     */
    public String getName() {
        return name;
    }


    String mips() {
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

