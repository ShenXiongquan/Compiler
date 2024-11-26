package frontend.node;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.type.IntegerType;
import frontend.llvm_ir.type.VoidType;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.myWriter;

public class FuncType extends node{
    public token returnType;

    public void print() {
        returnType.print();
        myWriter.writeNonTerminal("FuncType");
    }
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
