package frontend.node.initVal;


import frontend.llvm_ir.Value;
import frontend.node.node;

public abstract class InitVal extends node {

    public abstract String print();

    public abstract void visit(Value alloca);

}//变量初值
