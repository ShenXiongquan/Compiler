package frontend.node;

import frontend.Visitor;
import frontend.ir.constants.ConstInt;
import frontend.ir.type.IntegerType;
import frontend.token.token;
import frontend.tool.myWriter;

public class Number extends node {
    public token intConst;

    public void print() {
        intConst.print();
        myWriter.writeNonTerminal("Number");
    }

    @Override
    public void visit() {
        Visitor.upConstValue = Integer.parseInt(intConst.token());
        Visitor.upValue = new ConstInt(IntegerType.i32, Visitor.upConstValue);
    }
}//数值
