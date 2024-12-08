package frontend.llvm_ir.instructions;

import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.User;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.type.Type;

import java.util.ArrayList;

/**
 * 指令会对value进行操作，构成基本快，树的第四层。
 */
public abstract class Instruction extends User {
    /**
     * 指令所在的基本块
     */
    private final BasicBlock parentBlock;

    /**
     * @param name       指令的返回值名称
     * @param returnType 指令返回值类型
     * @param operands   指令的操作数
     */
    public Instruction(String name, Type returnType, ArrayList<Value> operands) {
        super(name, returnType, operands);
        this.parentBlock = Visitor.curBlock;
    }

    public String mips() {
        return "";
    }

    public BasicBlock getParentBlock() {
        return parentBlock;
    }
}
