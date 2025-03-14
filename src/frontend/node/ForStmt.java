package frontend.node;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.type.IntegerType;
import frontend.llvm_ir.type.PointerType;
import frontend.token.token;

public class ForStmt extends node {
    public LVal lVal;      // 左值
    public token assign;
    public Exp exp;        // 表达式

    public String print() {
        return lVal.print() +
                assign.print() +
                exp.print() +
                "<ForStmt>\n";
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
}//for循环语句
