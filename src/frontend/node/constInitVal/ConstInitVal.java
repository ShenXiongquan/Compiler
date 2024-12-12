package frontend.node.constInitVal;


import frontend.llvm_ir.Value;
import frontend.node.node;

public abstract class ConstInitVal extends node {

    public abstract String print();

    public abstract void visit(Value alloca);


}//常量初值
