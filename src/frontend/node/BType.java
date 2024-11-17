package frontend.node;

import frontend.token.token;

public class BType extends node{
    public token type;

    public void print(){
        type.print();
    }

    @Override
    public void visit() {

    }
}//基本类型
