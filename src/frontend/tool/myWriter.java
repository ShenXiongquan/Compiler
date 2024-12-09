package frontend.tool;

import java.io.FileWriter;
import java.io.IOException;

public class myWriter {

    // 文件路径常量，清晰反映用途
    private static final String LEXER_OUTPUT_FILE = "lexer.txt";
    private static final String PARSER_OUTPUT_FILE = "parser.txt";
    private static final String SYMBOL_TABLE_OUTPUT_FILE = "symbol.txt";
    private static final String LLVM_IR_OUTPUT_FILE = "llvm_ir.txt";
    private static final String MIPS_OUTPUT_FILE = "mips.txt";

    private static void writeToFile(String filePath, String content) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file: " + filePath, e);
        }
    }

    /**
     * 写入词法分析器结果
     *
     * @param tokens 词法分析结果
     */
    public static void writeTokens(String tokens) {
        writeToFile(LEXER_OUTPUT_FILE, tokens);
    }

    /**
     * 写入语法树结果
     *
     * @param tree 语法树结构
     */
    public static void writeTree(String tree) {
        System.out.print(tree);
        writeToFile(PARSER_OUTPUT_FILE, tree);
    }

    /**
     * 写入符号表
     *
     * @param symbolTable 符号表内容
     */
    public static void writeSymbolTable(String symbolTable) {
        writeToFile(SYMBOL_TABLE_OUTPUT_FILE, symbolTable);
    }

    /**
     * 写入 LLVM IR 中间代码
     *
     * @param ir LLVM IR 内容
     */
    public static void writeIr(String ir) {
        System.out.print(ir);
        writeToFile(LLVM_IR_OUTPUT_FILE, ir);
    }

    /**
     * 写入 MIPS 汇编代码
     *
     * @param mips MIPS 汇编内容
     */
    public static void writeMips(String mips) {
//        System.out.println(mips);
        writeToFile(MIPS_OUTPUT_FILE, mips);
    }
}
