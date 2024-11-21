package frontend.node.stmt;

import frontend.Visitor;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.instructions.MemInstructions.store;
import frontend.ir.type.IntegerType;
import frontend.ir.type.PointerType;
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

    @Override
    public void visit() {
        Visitor.lValNotLoad = true;
        lVal.visit();
        Visitor.lValNotLoad = false;
        Value dst = Visitor.upValue;
        exp.visit();
        Value src = Visitor.upValue;

        IntegerType expectedType = (IntegerType) ((PointerType) dst.getType()).getPointedType();
        if(src instanceof ConstInt) src=new ConstInt(expectedType,((ConstInt) src).getValue());
        else if (expectedType == IntegerType.i32) src = zext(src);
        else src = trunc(src);

        store store = new store(src, dst);
        Visitor.curBlock.addInstruction(store);
    }
}
