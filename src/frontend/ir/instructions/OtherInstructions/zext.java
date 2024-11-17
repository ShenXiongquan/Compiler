package frontend.ir.instructions.OtherInstructions;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.instructions.Instruction;
import frontend.ir.type.Type;

import java.util.ArrayList;

/**
 * <result> = zext <ty> <value> to <ty2>	将 ty 的 value 的 type 扩充为 ty2（zero extend）
 */
public class zext extends Instruction {
    /**
     * @param name      指令的返回值
     * @param valueType 指令返回值类型
     * @param operands  指令的操作数
     * @param block     指令的所在的块
     */
    public zext(String name, Type valueType, ArrayList<Value> operands, BasicBlock block) {
        super(name, valueType, operands, block);
    }

    @Override
    public String ir() {
        return null;
    }
}
