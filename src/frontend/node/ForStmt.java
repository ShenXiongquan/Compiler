package frontend.node;

import frontend.Visitor;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.instructions.MemInstructions.store;
import frontend.ir.type.IntegerType;
import frontend.ir.type.PointerType;
import frontend.token.token;
import frontend.tool.myWriter;

public class ForStmt extends node{
    public LVal lVal;      // 左值
    public token assign;
    public Exp exp;        // 表达式

    public void print(){
        lVal.print();
        assign.print();
        exp.print();
        myWriter.writeNonTerminal("ForStmt");
    }
    @Override
    public void visit() {
        lVal.visit();
        Value dst = Visitor.upValue;
        exp.visit();
        Value src = Visitor.upValue;

        IntegerType expectedType = (IntegerType) ((PointerType) dst.getType()).getPointedType();
        if (expectedType.isInt32()) src = zext(src);
        else src = trunc(src);

        store store = new store(src, dst);
        Visitor.curBlock.addInstruction(store);
    }
}//for循环语句
