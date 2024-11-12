package frontend.ir;

/**
 * llvm的基类，一切皆value
 * LLVM 中的标识符分为两种类型：全局的和局部的。
 * 全局的标识符包括函数名和全局变量，会加一个 @ 前缀，局部的标识符会加一个 % 前缀。
 * #0 指出了函数的 attribute group。由于每个函数的 attribute 很多，而且不同函数的 attributes 往往相同，
 * 因此将相同的 attributes 合并为一个 attribute group，从而使 IR 更加简洁清晰。
 */
public abstract class Value {


}
