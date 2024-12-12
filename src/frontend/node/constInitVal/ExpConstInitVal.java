package frontend.node.constInitVal;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.type.IntegerType;
import frontend.node.ConstExp;

public class ExpConstInitVal extends ConstInitVal {
    public ConstExp constExp;

    @Override
    public String print() {
        return constExp.print() +
                "<ConstInitVal>\n";
    }

    public void visit(Value alloca) {
        constExp.visit();
        Visitor.upValue = new ConstInt((IntegerType) Visitor.ValueType, Visitor.upConstValue);//全局和局部常量
    }
}
