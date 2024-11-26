package frontend.llvm_ir.constants;

import frontend.llvm_ir.type.ArrayType;

import java.util.Map;

public class ConstStr extends Constant {
    private static Map<String, String> llvmEscapeMap = Map.of(
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
    private final String str;

    public ConstStr(ArrayType strType, String str) {
        super(strType);

        // 用 StringBuilder 构建 LLVM 转义字符串
        StringBuilder llvmStr = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (c == '\\' && i + 1 < len) { // 检测转义字符
                char nextChar = str.charAt(i + 1);
                String escaped = (nextChar == '0')
                        ? "\\00"
                        : llvmEscapeMap.getOrDefault("\\" + nextChar, "\\" + nextChar);
                llvmStr.append(escaped);
                i++;
            } else {
                llvmStr.append(c);
            }
        }

        // 填充末尾的 \00，确保长度符合要求
        int padding = strType.getArraySize() - llvmStr.length() - 1;
        if (padding > 0) {
            llvmStr.append("\\00".repeat(padding));
        }

        this.str = llvmStr.toString();
    }


    @Override
    public String ir() {
        return "c\"" + str + "\\00\"";
    }


}
