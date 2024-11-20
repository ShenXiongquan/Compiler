package frontend.node;

import frontend.Visitor;
import frontend.ir.Parameter;
import frontend.ir.type.PointerType;
import frontend.token.token;
import frontend.tool.myWriter;

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

    @Override
    public void visit() {
        bType.visit();
        if (lbrack != null) {
            System.out.println("数组参数");
            Visitor.ValueType = new PointerType(Visitor.ValueType);
        }
        Parameter parameter = new Parameter(ident.token(),Visitor.ValueType);
        Visitor.parameters.add(parameter);
    }
}//函数形参FuncFParam → BType Ident ['[' ']']
