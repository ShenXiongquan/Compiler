package frontend.node.constInitVal;

import frontend.Visitor;
import frontend.ir.constants.ConstStr;
import frontend.ir.type.ArrayType;
import frontend.token.token;
import frontend.tool.myWriter;

public class StringConstInitVal extends ConstInitVal{
    public token stringConst;

    @Override
    public void print() {
        stringConst.print();
        myWriter.writeNonTerminal("ConstInitVal");
    }

    @Override
    public void visit() {
        String s=stringConst.token().substring(1, stringConst.token().length() - 1);
        Visitor.upValue=new ConstStr(new ArrayType(Visitor.ValueType,Visitor.ArraySize) , s);
    }
}
