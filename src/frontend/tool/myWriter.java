package frontend.tool;

import frontend.symbol.Symbol;
import frontend.token.token;

import java.io.FileWriter;
import java.io.IOException;

public class myWriter {

    private static final String outputFilePath1 = "lexer.txt";
    private static final String outputFilePath2 = "parser.txt";
    private static final String outputFilePath3 = "symbol.txt";
    private static final FileWriter writer;

    static {
        try {
            writer = new FileWriter(outputFilePath3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeTerminal(token token) {
        try {
            writer.write(token.getType() + " " + token.getToken() + "\n");
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

    public static void writeSymbol(Symbol symbol){
        try {
            writer.write(symbol.tableId+" "+symbol.token+" "+symbol.symbolType.getTypeName()+"\n");
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
