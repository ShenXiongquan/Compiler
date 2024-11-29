import frontend.Lexer;
import frontend.Parser;
import frontend.SemanticAnalyzer;
import frontend.llvm_ir.Visitor;
import frontend.node.CompUnit;
import frontend.token.token;
import frontend.tool.errorManager;
import frontend.tool.myWriter;

import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.List;

public class Compiler {
    private static final String inputFilePath = "testfile.txt";

    public static void main(String[] args) {
        try (PushbackReader reader = new PushbackReader(new FileReader(inputFilePath))){
            Lexer lexer = new Lexer(reader);
            List<token> tokens=lexer.getTokens();//词法分析
            Parser parser = new Parser(tokens);
            CompUnit compUnit= parser.parseCompUnit();//语法分析
            SemanticAnalyzer semanticAnalyzer=new SemanticAnalyzer(compUnit);
            semanticAnalyzer.visit();//语义分析
//          semanticAnalyzer.write();
            if(!errorManager.HasError())compUnit.visit();
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
