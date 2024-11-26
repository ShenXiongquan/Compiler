package frontend.node;

import frontend.Visitor;
import frontend.llvm_ir.Parameter;
import frontend.llvm_ir.type.PointerType;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;

public class FuncFParam extends node {
    public BType bType;
    public token ident;
    public token lbrack;
    public token rbrack;

    public void print() {
        bType.print();
        ident.print();
        if (lbrack != null) {
            lbrack.print();
            if (rbrack != null) rbrack.print();
        }
        myWriter.writeNonTerminal("FuncFParam");
    }


    public void visit(ArrayList<Parameter> parameters) {
        bType.visit();
        if (lbrack != null) {
            Visitor.ValueType = new PointerType(Visitor.ValueType);
        }
        Parameter parameter = new Parameter(ident.token(),Visitor.ValueType);
        parameters.add(parameter);
    }

}//函数形参FuncFParam → BType Ident ['[' ']']
