import frontend.Lexer;
import frontend.Parser;
import frontend.tool.errorManager;

import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

public class Compiler {
    private static final String inputFilePath = "testfile.txt";

    public static void main(String[] args) {
        try (PushbackReader reader = new PushbackReader(new FileReader(inputFilePath))
        ) {
            Lexer lexer = new Lexer(reader);
            Parser parser = new Parser(lexer);
            parser.parseCompUnit().visit();
            errorManager.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
