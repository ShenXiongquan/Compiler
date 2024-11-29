package frontend.node.initVal;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstArray;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.constants.Zeroinitializer;
import frontend.llvm_ir.type.ArrayType;
import frontend.llvm_ir.type.IntegerType;
import frontend.node.Exp;
import frontend.token.token;

import java.util.ArrayList;
import java.util.List;

public class ArrayInitVal extends InitVal {
    public token lbrace;
    public final List<Exp> exps = new ArrayList<>();
    public token comma;
    public token rbrace;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(lbrace.print());
        if (!exps.isEmpty()) {
            sb.append(exps.get(0).print());
            for (int i = 1; i < exps.size(); i++) {
                sb.append(comma.print());
                sb.append(exps.get(i).print());
            }
        }
        sb.append(rbrace.print());
        sb.append("<InitVal>\n");
        return sb.toString();
    }

    public void visit() {
        if (Visitor.isGlobal()) {//全局数组初始化
            if (exps.isEmpty()) {
                Visitor.upValue = new Zeroinitializer(new ArrayType(Visitor.ValueType, Visitor.ArraySize));
            } else {
                ArrayList<ConstInt> array = new ArrayList<>();
                for (int i = 0; i < Visitor.ArraySize; i++) {
                    if (i < exps.size()) {
                        exps.get(i).visit();
                        array.add(new ConstInt((IntegerType) Visitor.ValueType, Visitor.upConstValue));
                    } else {
                        array.add(new ConstInt((IntegerType) Visitor.ValueType, 0));
                    }
                }
                Visitor.upValue = new ConstArray(new ArrayType(Visitor.ValueType, Visitor.ArraySize), array);
            }
        } else {//局部数组初始化
            for (int i = 0; i < Visitor.ArraySize; i++) {
                if (i < exps.size()) {
                    exps.get(i).visit();
                    if (Visitor.ValueType.isInt32()) Visitor.upArrayValue.add(zext(Visitor.upValue));
                    else Visitor.upArrayValue.add(trunc(Visitor.upValue));

                } else {
                    Visitor.upArrayValue.add(new ConstInt((IntegerType) Visitor.ValueType, 0));
                }
            }
        }
    }
}
