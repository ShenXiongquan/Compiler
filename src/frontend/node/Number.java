package frontend.node;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.type.IntegerType;
import frontend.token.token;
import frontend.tool.myWriter;

public class Number extends node {
    public token intConst;

    public void print() {
        intConst.print();
        myWriter.writeNonTerminal("Number");
    }

    public void visit() {
        Visitor.upConstValue = Integer.parseInt(intConst.token());
        Visitor.upValue = new ConstInt(IntegerType.i32, Visitor.upConstValue);
    }
}//数值
