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


            if(Visitor.isGlobal()&&constExps.isEmpty()){//全局空数组
               Visitor.upValue=new Zeroinitializer(new ArrayType(Visitor.ValueType,Visitor.ArraySize));
            }else {//全局非空数组和局部常量数组
                ArrayList<ConstInt> array = new ArrayList<>();
                for (int i = 0; i < Visitor.ArraySize; i++) {
                    if(i<constExps.size()){
                        constExps.get(i).visit();
                        array.add(new ConstInt((IntegerType) Visitor.ValueType,Visitor.upConstValue));
                    }else {
                        array.add(new ConstInt((IntegerType) Visitor.ValueType, 0));
                    }
                }
                Visitor.upValue = new ConstArray(new ArrayType(Visitor.ValueType,Visitor.ArraySize),array);
            }
    }
}
