package frontend.node;

import frontend.Visitor;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.instructions.BinaryOperations.icmp;
import frontend.ir.type.IntegerType;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.myWriter;

public class RelExp extends node {

    public AddExp addExp;
    public RelExp relExp;
    public token relOp;//'<' | '>' | '<=' | '>='

    public void print(){
        if(relExp!=null){
            relExp.print();
            relOp.print();
        }
        addExp.print();
        myWriter.writeNonTerminal("RelExp");
    }
    @Override
    public void visit() {
        if(relExp!=null){
            relExp.visit();
            Value a=zext(Visitor.upValue);int leftConst = Visitor.upConstValue; // 保存左侧常量值
            addExp.visit();
            Value b=zext(Visitor.upValue);int rightConst = Visitor.upConstValue;
            if (a instanceof ConstInt && b instanceof ConstInt) {
                // 常量折叠优化
                System.out.println("左值:"+a.getName()+" "+leftConst+"右值:"+b.getName()+" "+rightConst);

                if (relOp.type() == tokenType.LSS) {
                    Visitor.upConstValue = (leftConst < rightConst) ? 1 : 0;
                } else if (relOp.type() == tokenType.LEQ) {
                    Visitor.upConstValue = (leftConst <= rightConst) ? 1 : 0;
                } else if (relOp.type() == tokenType.GRE) {
                    Visitor.upConstValue = (leftConst > rightConst) ? 1 : 0;
                } else { // GEQ
                    Visitor.upConstValue = (leftConst >= rightConst) ? 1 : 0;
                }
                System.out.println("比较结果值："+Visitor.upConstValue);
                Visitor.upValue = new ConstInt(IntegerType.i1, Visitor.upConstValue); // 设置布尔类型的常量值
            }else if(relOp.type()==tokenType.LSS){
                icmp ICMP=new icmp(icmp.LT,a,b);
                Visitor.curBlock.addInstruction(ICMP);
                Visitor.upValue=ICMP;
            } else if (relOp.type()==tokenType.LEQ) {
                icmp ICMP=new icmp(icmp.LE,a,b);
                Visitor.curBlock.addInstruction(ICMP);
                Visitor.upValue=ICMP;
            } else if (relOp.type()==tokenType.GRE) {
                icmp ICMP=new icmp(icmp.GT,a,b);
                Visitor.curBlock.addInstruction(ICMP);
                Visitor.upValue=ICMP;
            } else if (relOp.type()==tokenType.GEQ) {
                icmp ICMP=new icmp(icmp.GE,a,b);
                Visitor.curBlock.addInstruction(ICMP);
                Visitor.upValue=ICMP;
            }
        }else{
            addExp.visit();
        }
    }
}//关系表达式
