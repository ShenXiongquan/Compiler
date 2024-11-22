package frontend.node;

import frontend.Visitor;
import frontend.ir.type.IntegerType;
import frontend.token.token;
import frontend.token.tokenType;

public class BType extends node{
    public token type;

    public void print(){
        type.print();
    }

    @Override
    public void visit() {
        if(type.type()== tokenType.CHARTK){
            Visitor.ValueType= IntegerType.i8;
        }else{
            Visitor.ValueType= IntegerType.i32;
        }
    }
}//基本类型
