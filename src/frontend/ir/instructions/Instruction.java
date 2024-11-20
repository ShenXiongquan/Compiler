package frontend.ir.instructions;

import frontend.Visitor;
import frontend.ir.BasicBlock;
import frontend.ir.User;
import frontend.ir.Value;
import frontend.ir.type.Type;

import java.util.ArrayList;

/**
 * 指令会对value进行操作，构成基本快，树的第四层。
 */
public abstract class Instruction extends User {
    /**
     * 指令所在的基本块
     */
    BasicBlock parentBlock;

    /**
     * @param name       指令的返回值名称
     * @param returnType 指令返回值类型
     * @param operands   指令的操作数
     */
    public Instruction(String name, Type returnType, ArrayList<Value> operands) {
        super(name, returnType,operands);
        this.parentBlock= Visitor.curBlock;
    }
}
