package frontend.node.initVal;

import frontend.Visitor;
import frontend.ir.constants.ConstInt;
import frontend.ir.constants.ConstStr;
import frontend.ir.type.ArrayType;
import frontend.ir.type.IntegerType;
import frontend.token.token;
import frontend.tool.myWriter;

public class StringInitVal extends InitVal{
    public token stringConst;

    @Override
    public void print() {
        stringConst.print();
        myWriter.writeNonTerminal("InitVal");
    }

    @Override
    public void visit() {
        String s=stringConst.token().substring(1, stringConst.token().length() - 1);
        if(Visitor.isGlobal()){
            Visitor.upValue=new ConstStr((ArrayType)Visitor.ValueType,s);
        }else{
            for(char c:s.toCharArray()){
                Visitor.upArrayValue.add(new ConstInt(IntegerType.i8,c));
            }
        }
    }
}
