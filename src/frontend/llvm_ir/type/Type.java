package frontend.llvm_ir.type;


/**
 * 定义：Type类表示 LLVM IR 中所有 Value 的类型。每个 Value 都有一个 Type（如 i32、float、struct 等），它定义了 Value 的数据结构。
 * 角色：Type 类是所有值、变量、指令的基础类型信息。它定义了值如何存储和操作。
 */
public abstract class Type {

    /**
     * @return 类型的字节数
     */
    public abstract int getByteSize();

    public abstract String ir();

    public boolean isInt32() {
        return false;
    }

    public boolean isInt8() {
        return false;
    }

    public boolean isInt1() {
        return false;
    }

}
