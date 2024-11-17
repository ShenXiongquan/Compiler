package frontend.tool;

import frontend.symbol.Symbol;
import frontend.token.token;

import java.io.FileWriter;
import java.io.IOException;

public class myWriter {

    private static final String outputFilePath1 = "lexer.txt";
    private static final String outputFilePath2 = "parser.txt";
    private static final String outputFilePath3 = "symbol.txt";

    private static final String outputFilePath4 ="llvm_ir.txt";

    private static final String outputFilePath5 ="mips.txt";
    private static  FileWriter writer;

    static {
        try {
            writer = new FileWriter(outputFilePath4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public myWriter(String path){
        try {
            this.writer=new FileWriter(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeTerminal(token token) {
        try {
            writer.write(token.type() + " " + token.token() + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeNonTerminal(String NonTerminal) {
        try {
            writer.write("<" + NonTerminal + ">\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeSymbol(Symbol symbol) {
        try {
            writer.write(symbol.tableId + " " + symbol.token + " " + symbol.symbolType.getTypeName() + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeIr(String ir){
        try {
            writer.write(ir);
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
