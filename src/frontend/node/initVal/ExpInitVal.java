package frontend.node.initVal;


import frontend.Visitor;
import frontend.ir.constants.ConstInt;
import frontend.ir.type.IntegerType;
import frontend.node.Exp;
import frontend.tool.myWriter;

public class ExpInitVal extends InitVal{
    public Exp exp;

    @Override
    public void print() {
        exp.print();
        myWriter.writeNonTerminal("InitVal");
    }

    @Override
    public void visit() {
        exp.visit();
        if(Visitor.isGlobal()){//全局变量初始化
            Visitor.upValue=new ConstInt((IntegerType) Visitor.ValueType, Visitor.upConstValue);
        }else{//局部变量初始化
            if(Visitor.upValue instanceof ConstInt) Visitor.upValue=new ConstInt((IntegerType) Visitor.ValueType,((ConstInt) Visitor.upValue).getValue());
            else if (Visitor.ValueType.isInt32()) Visitor.upValue = zext(Visitor.upValue);
            else Visitor.upValue = trunc(Visitor.upValue);
        }
    }
}
