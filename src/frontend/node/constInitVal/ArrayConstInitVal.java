package frontend.node.constInitVal;

import frontend.Visitor;
import frontend.ir.constants.ConstArray;
import frontend.ir.constants.ConstInt;
import frontend.ir.constants.Zeroinitializer;
import frontend.ir.type.ArrayType;
import frontend.ir.type.IntegerType;
import frontend.node.ConstExp;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class ArrayConstInitVal extends ConstInitVal {

    public token lbrace;
    public final List<ConstExp> constExps = new ArrayList<>();

    public token comma;
    public token rbrace;

    @Override
    public void print() {
        lbrace.print();
        if (!constExps.isEmpty()) {
            constExps.get(0).print();
            for (int i = 1; i < constExps.size(); i++) {
                comma.print();
                constExps.get(i).print();
            }
        }
        rbrace.print();
        myWriter.writeNonTerminal("ConstInitVal");
    }

    @Override
    public void visit() {
            ArrayList<ConstInt> array = new ArrayList<>();

            if(constExps.isEmpty()&&Visitor.isGlobal()){//没有显示初值，初始化为0
                Visitor.upValue=new Zeroinitializer((ArrayType) Visitor.ValueType);
            }else {//初始化常量数组元素
                int num = ((ArrayType) Visitor.ValueType).getElementNum();
                IntegerType integerType= (IntegerType) ((ArrayType) Visitor.ValueType).getElementType();

                for (int i = 0; i < num; i++) {
                    if(i<constExps.size()){
                        constExps.get(i).visit();
                        array.add(new ConstInt(integerType,Visitor.upConstValue));
                    }else {
                        array.add(new ConstInt(integerType, 0));
                    }

                }
                Visitor.upValue = new ConstArray((ArrayType) Visitor.ValueType,array);
            }
    }
}
