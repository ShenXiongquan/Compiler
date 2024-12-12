package frontend.node.constInitVal;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstArray;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.constants.Zeroinitializer;
import frontend.llvm_ir.instructions.MemInstructions.getelementptr;
import frontend.llvm_ir.type.ArrayType;
import frontend.llvm_ir.type.IntegerType;
import frontend.node.ConstExp;
import frontend.token.token;

import java.util.ArrayList;
import java.util.List;

public class ArrayConstInitVal extends ConstInitVal {

    public final List<ConstExp> constExps = new ArrayList<>();
    public token lbrace;
    public token comma;
    public token rbrace;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(lbrace.print());
        if (!constExps.isEmpty()) {
            sb.append(constExps.get(0).print());
            for (int i = 1; i < constExps.size(); i++) {
                sb.append(comma.print());
                sb.append(constExps.get(i).print());
            }
        }
        sb.append(rbrace.print());
        sb.append("<ConstInitVal>\n");
        return sb.toString();
    }

    public void visit(Value alloca) {

        if (Visitor.isGlobal()) {//全局数组初始化
            if (constExps.isEmpty()) {
                Visitor.upValue = new Zeroinitializer(new ArrayType(Visitor.ValueType, Visitor.ArraySize));
            } else {
                ArrayList<ConstInt> array = new ArrayList<>();
                for (int i = 0; i < Visitor.ArraySize; i++) {
                    if (i < constExps.size()) {
                        constExps.get(i).visit();
                        array.add(new ConstInt((IntegerType) Visitor.ValueType, Visitor.upConstValue));
                    } else {
                        array.add(new ConstInt((IntegerType) Visitor.ValueType, 0));
                    }
                }
                Visitor.upValue = new ConstArray(new ArrayType(Visitor.ValueType, Visitor.ArraySize), array);
            }
        } else {//局部数组初始化
            for (int i = 0; i < Visitor.ArraySize; i++) {
                if (i < constExps.size()) {
                    constExps.get(i).visit();
                    Visitor.upValue = new ConstInt((IntegerType) Visitor.ValueType, Visitor.upConstValue);
                } else {
                    Visitor.upValue = new ConstInt((IntegerType) Visitor.ValueType, 0);
                }
                getelementptr getelementptr = getelementptr(alloca, ConstInt.zero, new ConstInt(IntegerType.i32, i));
                store(Visitor.upValue, getelementptr);
            }
        }
    }
}

//     if (Visitor.isGlobal() && constExps.isEmpty()) {//全局空数组
//             Visitor.upValue = new Zeroinitializer(new ArrayType(Visitor.ValueType, Visitor.ArraySize));
//      } else {//全局非空数组和局部常量数组
//        ArrayList<ConstInt> array = new ArrayList<>();
//        for (int i = 0; i < Visitor.ArraySize; i++) {
//        if (i < constExps.size()) {
//        constExps.get(i).visit();
//        array.add(new ConstInt((IntegerType) Visitor.ValueType, Visitor.upConstValue));
//        } else {
//          array.add(new ConstInt((IntegerType) Visitor.ValueType, 0));
//        }
//        }
//        Visitor.upValue = new ConstArray(new ArrayType(Visitor.ValueType, Visitor.ArraySize), array);
//     }