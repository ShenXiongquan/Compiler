package frontend.node.stmt;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.instructions.MixedInstructions.call;
import frontend.llvm_ir.type.IntegerType;
import frontend.llvm_ir.type.PointerType;
import frontend.node.LVal;
import frontend.token.token;
import frontend.tool.myWriter;

public class GetIntStmt extends Stmt {
    public LVal lVal;
    public token getint;

    public token assign;
    public token lparent;
    public token rparent;
    public token semicn;

    @Override
    public void print() {
        lVal.print();
        assign.print();
        getint.print();
        lparent.print();
        if (rparent != null) rparent.print();
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("Stmt");
    }
    public void visit() {
        lVal.visit();
        call call=call(Visitor.model.getint());
        IntegerType expectedType= (IntegerType) ((PointerType)Visitor.upValue.getType()).getPointedType();
        store(expectedType.isInt8()?trunc(call):call,Visitor.upValue);

    }
}
