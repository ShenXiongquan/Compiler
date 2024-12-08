package frontend.node.initVal;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstArray;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.type.ArrayType;
import frontend.llvm_ir.type.IntegerType;
import frontend.token.token;

import java.util.ArrayList;

public class StringInitVal extends InitVal {
    public token stringConst;

    @Override
    public String print() {
        return stringConst.print() +
                "<InitVal>\n";
    }

    public void visit() {
        String s = stringConst.name().substring(1, stringConst.name().length() - 1);
        if (Visitor.isGlobal()) {
            ArrayList<ConstInt> array = new ArrayList<>();
            for (int i = 0; i < Visitor.ArraySize; i++) {
                if (i < s.length()) array.add(i, new ConstInt(IntegerType.i8, s.charAt(i)));
                else array.add(i, ConstInt.zeroI8);
            }
            Visitor.upValue = new ConstArray(new ArrayType(Visitor.ValueType, Visitor.ArraySize), array);
//            Visitor.upValue = new ConstStr(new ArrayType(Visitor.ValueType, Visitor.ArraySize), s);
        } else {
            Visitor.upArrayValue = new ArrayList<>();
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
