package frontend.node;

import frontend.Visitor;
import frontend.ir.type.IntegerType;
import frontend.ir.type.VoidType;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.myWriter;

public class FuncType extends node{
    public token returnType;

    public void print() {
        returnType.print();
        myWriter.writeNonTerminal("FuncType");
    }
    @Override
    public void visit() {
        if(returnType.type()== tokenType.VOIDTK){
            Visitor.returnType = new VoidType();
        } else if (returnType.type()==tokenType.INTTK) {
            Visitor.returnType=new IntegerType(32);
        }else {
            Visitor.returnType=new IntegerType(8);
        }
    }
}//函数返回值类型
