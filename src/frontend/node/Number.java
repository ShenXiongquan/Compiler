package frontend.node;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.type.IntegerType;
import frontend.token.token;

public class Number extends node {
    public token intConst;

    public String print() {
        return intConst.print() +
                "<Number>\n";
    }


    public void visit() {
        Visitor.upConstValue = Integer.parseInt(intConst.name());
        Visitor.upValue = new ConstInt(IntegerType.i32, Visitor.upConstValue);
    }
}//数值
