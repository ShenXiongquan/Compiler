package frontend.llvm_ir.instructions.MixedInstructions;

import frontend.llvm_ir.Function;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.instructions.Instruction;
import frontend.llvm_ir.type.VoidType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <result> = call [ret attrs] <ty> <name>(<...args>)	函数调用
 */
public class call extends Instruction {

    /**
     * 构造函数：创建函数调用指令
     *
     * @param function 被调用的函数 (作为操作数的第一个值)
     * @param args     函数调用的参数列表
     */
    public call(Function function, Value... args) {
        super(
                function.getType().getReturnType() instanceof VoidType ? null : LOCAL_PREFIX + (Function.VarNum++),
                function.getType().getReturnType(),
                new ArrayList<>() {{
                    add(function); // 将函数作为第一个操作数
                    addAll(List.of(args));  // 将参数列表追加到操作数中
                }}
        );
    }

    /**
     * 生成 LLVM IR 格式的函数调用指令
     *
     * @return LLVM IR 格式的字符串
     */
    @Override
    public String ir() {
        Function function = (Function) getOperands().get(0);

        String args = getOperands().subList(1, getOperands().size()).stream()
                .map(op -> op.getType().ir() + " " + op.getName())
                .collect(Collectors.joining(", "));

        // 返回值处理: 如果返回值是 void 则无 result
        String result = getType() instanceof VoidType ? "" : (getName() + " = ");

        // 生成指令
        return result + "call "
                + function.getType().getReturnType().ir() + " "
                + function.getName() + "(" + args + ")";
    }
}
