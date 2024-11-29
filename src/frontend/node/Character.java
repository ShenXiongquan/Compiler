package frontend.node;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.type.IntegerType;
import frontend.token.token;

public class Character extends node {
    public token charConst;

    public String print() {
        return charConst.print() +
                "<Character>\n";
    }

    public void visit() {
        int c;
        String s = charConst.name().substring(1, charConst.name().length() - 1);

        c = switch (s) {
            case "\\a" -> 7;// 响铃
            case "\\b" -> 8;// 退格
            case "\\t" -> 9;// 制表符
            case "\\n" -> 10;// 换行
            case "\\v" -> 11;// 垂直制表符
            case "\\f" -> 12;// 换页
            case "\\r" -> 13; // 回车
            case "\\\"" -> 34; // 双引号
            case "\\'" -> 39;// 单引号
            case "\\\\" -> 92;// 反斜杠
            case "\\0" -> 0;// 空字符
            default -> s.charAt(0);
        };
        Visitor.upConstValue = c;
        Visitor.upValue = new ConstInt(IntegerType.i32, Visitor.upConstValue);
    }
}//字符
