package frontend.node;

import frontend.llvm_ir.Parameter;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.type.PointerType;
import frontend.token.token;

import java.util.ArrayList;

public class FuncFParam extends node {
    public BType bType;
    public token ident;
    public token lbrack;
    public token rbrack;

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(bType.print());
        sb.append(ident.print());
        if (lbrack != null) {
            sb.append(lbrack.print());
            if (rbrack != null) {
                sb.append(rbrack.print());
            }
        }
        sb.append("<FuncFParam>\n");
        return sb.toString();
    }


    public void visit(ArrayList<Parameter> parameters) {
        bType.visit();
        if (lbrack != null) {
            Visitor.ValueType = new PointerType(Visitor.ValueType);
        }
        Parameter parameter = new Parameter(ident.name(), Visitor.ValueType);
        parameters.add(parameter);
    }

}//函数形参FuncFParam → BType Ident ['[' ']']
