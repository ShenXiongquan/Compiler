package frontend.node.stmt;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.IntegerType;
import frontend.llvm_ir.type.PointerType;
import frontend.node.Exp;
import frontend.node.LVal;
import frontend.token.token;
import frontend.tool.myWriter;

public class AssignStmt extends Stmt {
    public LVal lVal;
    public Exp exp;
    public token assign;
    public token semicn;

    @Override
    public void print() {
        lVal.print();
        assign.print();
        exp.print();
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("Stmt");
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
