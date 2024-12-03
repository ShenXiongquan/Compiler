package frontend.node.constInitVal;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.constants.ConstStr;
import frontend.llvm_ir.type.ArrayType;
import frontend.llvm_ir.type.IntegerType;
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
        if (Visitor.isGlobal()) {
            Visitor.upValue = new ConstStr(new ArrayType(Visitor.ValueType, Visitor.ArraySize), s);
        } else {
            for (int i = 0; i < Visitor.ArraySize; i++) {
                if (i < s.length()) {
                    Visitor.upArrayValue.add(new ConstInt(IntegerType.i8, s.charAt(i)));
                } else {
                    Visitor.upArrayValue.add(ConstInt.zeroI8);
                }
            }
        }
    }
}
