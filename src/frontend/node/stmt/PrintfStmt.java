package frontend.node.stmt;

import frontend.Visitor;
import frontend.llvm_ir.GlobalVariable;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.constants.ConstStr;
import frontend.llvm_ir.instructions.MemInstructions.getelementptr;
import frontend.llvm_ir.instructions.MixedInstructions.call;
import frontend.llvm_ir.type.ArrayType;
import frontend.llvm_ir.type.IntegerType;
import frontend.node.Exp;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.*;

public class PrintfStmt extends Stmt {
    public token printf;
    public token stringConst;
    public final List<Exp> exps=new ArrayList<>();

    public token comma;

    public token lparent;
    public token rparent;
    public token semicn;

    @Override
    public void print() {
        printf.print();
        lparent.print();
        stringConst.print();
        if (!exps.isEmpty()) for (Exp exp : exps) {
            comma.print();
            exp.print();
        }
        if (rparent != null) rparent.print();
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("Stmt");
    }
    public void visit() {
        // 获取原始字符串并解析为分段
        String rawString = stringConst.token().substring(1, stringConst.token().length() - 1);
        List<String> parts = parseString(rawString);

        // 遍历分段，根据类型生成相应的指令
        for (int i = 0, j = 0; i < parts.size(); i++) {
            String segment = parts.get(i);

            if ("%d".equals(segment) || "%c".equals(segment)) {
                // 处理占位符 (%d 和 %c)
                exps.get(j++).visit();
                Value output = zext(Visitor.upValue);
                call printCall = new call(
                        "%d".equals(segment) ? Visitor.model.putint() : Visitor.model.putchar(),
                        output
                );
                Visitor.curBlock.addInstruction(printCall);
            } else {
                // 处理普通字符串部分
                GlobalVariable globalStr = getGlobalString(segment);
                getelementptr gep = new getelementptr(globalStr, ConstInt.zeroI64, ConstInt.zeroI64);
                Visitor.curBlock.addInstruction(gep);
                call printCall = new call(Visitor.model.putstr(), gep);
                Visitor.curBlock.addInstruction(printCall);
            }
        }
    }

    /**
     * 将字符串解析为占位符和普通字符串的分段。
     */
    private List<String> parseString(String rawString) {
        List<String> parts = new ArrayList<>();
        int lastIndex = 0;

        for (int i = 0; i < rawString.length(); i++) {
            if (rawString.charAt(i) == '%' && i + 1 < rawString.length()) {
                if (lastIndex < i) {
                    parts.add(rawString.substring(lastIndex, i)); // 添加普通字符串部分
                }
                parts.add("%" + rawString.charAt(i + 1)); // 添加占位符
                i++;
                lastIndex = i + 1;
            }
        }

        if (lastIndex < rawString.length()) {
            parts.add(rawString.substring(lastIndex)); // 添加剩余普通字符串部分
        }

        return parts;
    }

    /**
     * 获取或创建全局字符串变量。
     */
    private GlobalVariable getGlobalString(String constStr) {
        if (!Visitor.stringPool.containsKey(constStr)) {
            // 计算字符串长度并创建全局变量
            int adjustedLength = constStr.replaceAll("\\\\", "").length()+1;
            System.out.println("adjustedLength:"+adjustedLength);
            GlobalVariable globalStr = new GlobalVariable(new ConstStr(new ArrayType(IntegerType.i8, adjustedLength), constStr));
            Visitor.model.addGlobalStr(globalStr);
            Visitor.stringPool.put(constStr, globalStr);
        }
        return Visitor.stringPool.get(constStr);
    }
}
