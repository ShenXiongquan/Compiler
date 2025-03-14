import backend.Translator;
import backend.module.MIPSModel;
import frontend.Lexer;
import frontend.Parser;
import frontend.SemanticAnalyzer;
import frontend.llvm_ir.Visitor;
import frontend.node.CompUnit;
import frontend.token.token;
import frontend.tool.errorManager;
import frontend.tool.myWriter;

import java.util.List;

public class Compiler {

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        List<token> tokens = lexer.getTokens();//词法分析
        Parser parser = new Parser(tokens);
        CompUnit compUnit = parser.getCompUnit();//语法分析
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(compUnit);
        semanticAnalyzer.visit();//语义分析
        errorManager.write();
        if (!errorManager.HasError()) {
            Visitor visitor = new Visitor(compUnit);
            visitor.visit();//中间代码生成
            myWriter.writeIr(Visitor.model.ir());
            Translator Translator = new Translator(Visitor.model);
            MIPSModel mipsModel = Translator.genMips();//目标代码生成
            myWriter.writeMips(mipsModel.mips());
        }
    }
}
