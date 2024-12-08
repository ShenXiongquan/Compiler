package frontend.llvm_ir;

import frontend.llvm_ir.type.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * llvm的基类，一切皆value
 * 在 LLVM 的设计中，Value 是所有“值”的基类，包括常量、变量、指令等。它的主要任务是提供统一的接口，让各种值类型都能在 IR 中使用，并且可以互相引用、追踪使用者（User）
 * 每一个value都是或大或小的单元，小的value组成大的value
 */
public abstract class Value {

    protected static final String GLOBAL_PREFIX = "@";
    protected static final String LOCAL_PREFIX = "%";
    /**
     * 临时变量命名,临时变量只在函数的形参和instruction中产生
     */

    protected final String llvm_name;//value的llvm表示
    /**
     * 一切value皆有自己的类型
     */
    private final Type type;//value的类型
    /**
     * 使用value的user
     */
    private final List<User> users = new ArrayList<>();

    public Value(String llvm_name, Type valueType) {
        this.llvm_name = llvm_name;
        this.type = valueType;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    /**
     * @return value的类型
     */
    public Type getType() {
        return type;
    }

    /**
     * @return value的名称
     */
    public String getName() {
        return llvm_name;
    }

    protected abstract String ir();
}

//    public static final String GLOBAL_NAME_PREFIX = "g_";
//    public static final String LOCAL_NAME_PREFIX = "v";
//    public static final String FPARAM_NAME_PREFIX = "f";