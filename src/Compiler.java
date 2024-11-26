import frontend.Lexer;
import frontend.Parser;
import frontend.Visitor;
import frontend.node.CompUnit;
import frontend.tool.errorManager;
import frontend.tool.myWriter;

import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

public class Compiler {
    private static final String inputFilePath = "testfile.txt";

    public static void main(String[] args) {
        try (PushbackReader reader = new PushbackReader(new FileReader(inputFilePath))){
            Lexer lexer = new Lexer(reader);//词法分析
            Parser parser = new Parser(lexer.getTokens());//语法分析
            CompUnit compUnit= parser.parseCompUnit();
            Visitor visitor=new Visitor();//语义分析
//            visitor.visit(compUnit);
            compUnit.visit();
            System.out.print(Visitor.model.ir());
            myWriter.writeIr(Visitor.model.ir());
            errorManager.write();
            myWriter.close();
            errorManager.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
