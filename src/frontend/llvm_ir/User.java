package frontend.llvm_ir;

import frontend.llvm_ir.type.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义：User类是Value类的子类，表示使用了一个或多个 Value 的对象。简单来说，User是指依赖于其他值的操作或指令，如Instruction（指令）、ConstantExpr（常量表达式）等。
 * 角色：User类是LLVM中所有指令和表达式的基类。任何需要使用其他 Value 作为输入的对象都是 User 的子类。
 */
public abstract class User extends Value {

    //操作数可以是常量、本地变量、全局变量、指令、函数参数
    private final List<Value> operands = new ArrayList<>();

    public User(String name, Type valueType, ArrayList<Value> operands) {
        super(name, valueType);
        this.operands.addAll(operands);
        for (Value value : operands) {
            if (value != null) {
                value.addUser(this);
            }
        }
    }


    /**
     * 获取操作数
     *
     * @param index 操作数位置
     */
    public Value getOperand(int index) {
        if (index < 0 || index >= operands.size()) {
            throw new IndexOutOfBoundsException("Invalid operand index: " + index);
        }
        return operands.get(index);
    }

    /**
     * @return 操作数数组
     */
    public List<Value> getOperands() {
        return new ArrayList<>(operands);  // 返回副本以防止外部修改
    }
}
