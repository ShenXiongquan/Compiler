package frontend.node.stmt;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.type.IntegerType;
import frontend.llvm_ir.type.PointerType;
import frontend.node.Exp;
import frontend.node.LVal;
import frontend.token.token;

public class AssignStmt extends Stmt {
    public LVal lVal;
    public Exp exp;
    public token assign;
    public token semicn;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(lVal.print());
        sb.append(assign.print());
        sb.append(exp.print());
        if (semicn != null) sb.append(semicn.print());
        sb.append("<Stmt>\n");
        return sb.toString();
    }

    public void visit() {
        lVal.visit();
        Value dst = Visitor.upValue;
        exp.visit();
        Value src = Visitor.upValue;

        IntegerType expectedType = (IntegerType) ((PointerType) dst.getType()).getPointedType();
        if (expectedType.isInt32()) src = zext(src);
        else src = trunc(src);

        store(src, dst);
    }
}
