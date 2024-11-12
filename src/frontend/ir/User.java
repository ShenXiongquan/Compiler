package frontend.ir;

/**
 * value 的使用者，A: %add1 = add nsw i32 %a, %b
 * A是一条 Instruction，它在代码中的体现就是 %add1，即指令的返回值，也称作一个虚拟寄存器。
 * Instruction 继承自 User，因此它可以将其他 Value（%a 和 %b）作为参数。
 * 因此，在 %add1 与 %a、%b 之间分别构成了 Use 关系。
 */
public abstract class User extends Value {
}
