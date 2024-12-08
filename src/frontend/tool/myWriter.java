package frontend.tool;

import frontend.symbol.Symbol;
import frontend.token.token;

import java.io.FileWriter;
import java.io.IOException;

public class myWriter {

    private static final String outputFilePath1 = "lexer.txt";
    private static final String outputFilePath2 = "parser.txt";
    private static final String outputFilePath3 = "symbol.txt";
    private static final String outputFilePath4 = "llvm_ir.txt";

    private static final String outputFilePath5 = "mips.txt";
    private static FileWriter writer;

    static {
        try {
            writer = new FileWriter(outputFilePath4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public myWriter(String path) {
        try {
            writer = new FileWriter(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeTerminal(token token) {
        System.out.println(token.type() + " " + token.name());
        try {
            writer.write(token.type() + " " + token.name() + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeTree(String tree) {
        System.out.print(tree);
        try {
            writer.write(tree);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeSymbol(Symbol symbol) {
        StringBuilder type = new StringBuilder();
        if (symbol.isConst()) type.append("Const");
        type.append(symbol.getSymbolType());
        if (symbol.isArray()) type.append("Array");
        if (symbol.getParamList() != null) type.append("Func");
        String output = symbol.getTableId() + " " + symbol.getToken() + " " + type;
        System.out.println(output);

        try {
            writer.write(output + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeIr(String ir) {
        System.out.print(ir);
        try {
            writer = new FileWriter(outputFilePath4);
            writer.write(ir);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeMips(String mips) {
//        System.out.println(mips);
        try {
            writer = new FileWriter(outputFilePath5);
            writer.write(mips);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
