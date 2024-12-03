package frontend.llvm_ir.constants;

import frontend.llvm_ir.type.ArrayType;

import java.util.HashMap;

public class ConstStr extends Constant {
    private static final HashMap<Character, String> llvmEscapeMap = new HashMap<>();

    static {
        llvmEscapeMap.put('0', "00");
        llvmEscapeMap.put('a', "07"); // Alert (bell)
        llvmEscapeMap.put('b', "08"); // Backspace
        llvmEscapeMap.put('t', "09"); // Horizontal Tab
        llvmEscapeMap.put('n', "0A"); // Newline
        llvmEscapeMap.put('v', "0B"); // Vertical Tab
        llvmEscapeMap.put('f', "0C"); // Form Feed
        llvmEscapeMap.put('r', "0D"); // Carriage Return
        llvmEscapeMap.put('\"', "22"); // Double Quote
        llvmEscapeMap.put('\'', "27"); // Single Quote
        llvmEscapeMap.put('\\', "5C"); // Backslash
    }

    private final String str;

    private final String llvm_str;

    public ConstStr(ArrayType strType, String str) {
        super(strType);
        this.str = str;
        // 用 StringBuilder 构建 LLVM 转义字符串
        StringBuilder llvmStr = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            llvmStr.append(c);
            if (c == '\\' && i + 1 < len) { // 检测转义字符
                llvmStr.append(llvmEscapeMap.get(str.charAt(++i)));
            }
        }

        // 填充末尾的 \00，确保长度符合要求
        int padding = strType.getArraySize() - llvmStr.length() - 1;
        if (padding > 0) {
            llvmStr.append("\\00".repeat(padding));
        }

        this.llvm_str = llvmStr.toString();
    }


    @Override
    public String ir() {
        return "c\"" + llvm_str + "\\00\"";
    }

    public String mips() {
        return ".asciiz \"" + str + "\"";
    }
}
