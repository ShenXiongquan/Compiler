package frontend.node.stmt;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.instructions.MixedInstructions.call;
import frontend.llvm_ir.type.IntegerType;
import frontend.llvm_ir.type.PointerType;
import frontend.node.LVal;
import frontend.token.token;

public class GetIntStmt extends Stmt {
    public LVal lVal;
    public token getint;

    public token assign;
    public token lparent;
    public token rparent;
    public token semicn;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(lVal.print());
        sb.append(assign.print());
        sb.append(getint.print());
        sb.append(lparent.print());
        if (rparent != null) sb.append(rparent.print());
        if (semicn != null) sb.append(semicn.print());
        sb.append("<Stmt>\n");
        return sb.toString();
    }


    public void visit() {
        lVal.visit();
        call call = call(Visitor.model.getint());
        IntegerType expectedType = (IntegerType) ((PointerType) Visitor.upValue.getType()).getPointedType();
        store(expectedType.isInt8() ? trunc(call) : call, Visitor.upValue);

    }
}
