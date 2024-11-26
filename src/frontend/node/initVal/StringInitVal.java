package frontend.node.initVal;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.constants.ConstStr;
import frontend.llvm_ir.type.ArrayType;
import frontend.llvm_ir.type.IntegerType;
import frontend.token.token;
import frontend.tool.myWriter;

public class StringInitVal extends InitVal {
    public token stringConst;

    @Override
    public void print() {
        stringConst.print();
        myWriter.writeNonTerminal("InitVal");
    }

    public void visit() {
        String s = stringConst.token().substring(1, stringConst.token().length() - 1);
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
