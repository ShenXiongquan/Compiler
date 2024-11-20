package frontend.node.initVal;

import frontend.Visitor;
import frontend.ir.constants.ConstArray;
import frontend.ir.constants.ConstInt;
import frontend.ir.constants.Zeroinitializer;
import frontend.ir.type.ArrayType;
import frontend.ir.type.IntegerType;
import frontend.node.Exp;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class ArrayInitVal extends InitVal {
    public token lbrace;
    public final List<Exp> exps = new ArrayList<>();
    public token comma;
    public token rbrace;

    @Override
    public void print() {
        lbrace.print();
        if (!exps.isEmpty()) {
            exps.get(0).print();
            for (int i = 1; i < exps.size(); i++) {
                comma.print();
                exps.get(i).print();
            }
        }
        rbrace.print();
        myWriter.writeNonTerminal("InitVal");
    }

    @Override
    public void visit() {
        if (Visitor.isGlobal()) {//全局数组初始化
            if (exps.isEmpty()) {
                Visitor.upValue = new Zeroinitializer((ArrayType) Visitor.ValueType);
            } else {
                ArrayList<ConstInt> array = new ArrayList<>();
                int num = ((ArrayType) Visitor.ValueType).getElementNum();
                IntegerType elementType = ((ArrayType) Visitor.ValueType).getElementType();
                for (int i = 0; i < num; i++) {
                    if (i < exps.size()) {
                        exps.get(i).visit();
                        array.add(new ConstInt(elementType, Visitor.upConstValue));
                    } else {
                        array.add(new ConstInt(elementType, 0));
                    }
                }
                Visitor.upValue = new ConstArray((ArrayType) Visitor.ValueType, array);
            }
        } else {//局部数组初始化
            for (Exp exp : exps) {
                exp.visit();
                IntegerType elementType = ((ArrayType) Visitor.ValueType).getElementType();
                if (Visitor.upValue instanceof ConstInt) Visitor.upValue = new ConstInt(elementType, ((ConstInt) Visitor.upValue).getValue());
                else if (elementType.isInt32()) Visitor.upValue = zext(Visitor.upValue);
                else Visitor.upValue = trunc(Visitor.upValue);

                Visitor.upArrayValue.add(Visitor.upValue);
            }
        }
    }
}
