package frontend.node.constInitVal;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstStr;
import frontend.llvm_ir.type.ArrayType;
import frontend.token.token;

public class StringConstInitVal extends ConstInitVal {
    public token stringConst;

    @Override
    public String print() {
        return stringConst.print() +
                "<ConstInitVal>\n";
    }

    public void visit() {
        String s = stringConst.name().substring(1, stringConst.name().length() - 1);
        Visitor.upValue = new ConstStr(new ArrayType(Visitor.ValueType, Visitor.ArraySize), s);
    }
}
