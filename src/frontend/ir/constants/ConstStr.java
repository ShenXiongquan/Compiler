package frontend.ir.constants;

import frontend.ir.type.ArrayType;
import frontend.ir.type.IntegerType;

import java.util.Map;

public class ConstStr extends Constant {

    private final String str;

    public ConstStr(ArrayType strType, String str) {
        super(strType);

        // 转义字符映射表（LLVM IR 格式）
        Map<String, String> llvmEscapeMap = Map.of(
                "\\a", "\\07", // Alert (bell)
                "\\b", "\\08", // Backspace
                "\\t", "\\09", // Horizontal Tab
                "\\n", "\\0A", // Newline
                "\\v", "\\0B", // Vertical Tab
                "\\f", "\\0C", // Form Feed
                "\\r", "\\0D", // Carriage Return
                "\\\"", "\\22", // Double Quote
                "\\'", "\\27", // Single Quote
                "\\\\", "\\5C"// Backslash
                );

        StringBuilder llvmStr = new StringBuilder();
        int i = 0, len = str.length();

        // 转义输入字符串
        while (i < len) {
            char c = str.charAt(i);
            if (c == '\\' && i + 1 < len) { // 检测转义字符
                llvmStr.append(llvmEscapeMap.get("\\" + str.charAt(i + 1))); // 转换为 LLVM IR 格式
                i += 2; // 跳过转义序列
            } else {
                llvmStr.append(c); // 可打印字符，直接添加
            }
            i++;
        }
        // 填充 \00 到指定长度
        llvmStr.append("\\00".repeat(Math.max(0, strType.getElementNum() -len-1)));
        this.str = llvmStr.toString();
    }

    @Override
    public String ir() {
        return "c\"" + str + "\\00\"";
    }

    @Override
    public String getName() {
        return ir();
    }
}
