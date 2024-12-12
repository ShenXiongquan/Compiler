package frontend.node.initVal;


import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.type.IntegerType;
import frontend.node.Exp;

public class ExpInitVal extends InitVal {
    public Exp exp;

    @Override
    public String print() {
        return exp.print() +
                "<InitVal>\n";
    }

    public void visit(Value alloca) {
        exp.visit();
        if (Visitor.isGlobal()) {//全局变量初始化
            Visitor.upValue = new ConstInt((IntegerType) Visitor.ValueType, Visitor.upConstValue);
        } else {//局部变量初始化
            if (Visitor.ValueType.isInt32()) Visitor.upValue = zext(Visitor.upValue);
            else Visitor.upValue = trunc(Visitor.upValue);
        }
    }


}
