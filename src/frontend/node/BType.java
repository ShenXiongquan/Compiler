package frontend.node;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.type.IntegerType;
import frontend.token.token;
import frontend.token.tokenType;

public class BType extends node{
    public token type;

    public void print(){
        type.print();
    }

    public void visit() {
        if(type.type()== tokenType.CHARTK){
            Visitor.ValueType= IntegerType.i8;
        }else{
            Visitor.ValueType= IntegerType.i32;
        }
    }
}//基本类型
