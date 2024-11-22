package frontend.node.stmt;

import frontend.Visitor;
import frontend.ir.instructions.MemInstructions.store;
import frontend.ir.instructions.MixedInstructions.call;
import frontend.ir.type.IntegerType;
import frontend.ir.type.PointerType;
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
    @Override
    public void visit() {
        lVal.visit();
        call call=new call(Visitor.model.getint());
        Visitor.curBlock.addInstruction(call);
        IntegerType expectedType= (IntegerType) ((PointerType)Visitor.upValue.getType()).getPointedType();
        store store=new store(expectedType.isInt8()?trunc(call):call,Visitor.upValue);
        Visitor.curBlock.addInstruction(store);
    }
}
