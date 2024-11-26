package frontend.node.constInitVal;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstStr;
import frontend.llvm_ir.type.ArrayType;
import frontend.token.token;
import frontend.tool.myWriter;

public class StringConstInitVal extends ConstInitVal{
    public token stringConst;

    @Override
    public void print() {
        stringConst.print();
        myWriter.writeNonTerminal("ConstInitVal");
    }

    public void visit() {
        String s=stringConst.token().substring(1, stringConst.token().length() - 1);
        Visitor.upValue=new ConstStr(new ArrayType(Visitor.ValueType,Visitor.ArraySize) , s);
    }
}
