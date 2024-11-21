package frontend.node;

import frontend.Visitor;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.instructions.BinaryOperations.icmp;
import frontend.ir.type.IntegerType;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.myWriter;

public class EqExp extends node{
    public RelExp relExp;
    public token eqOp;//'==' | '!='
    public EqExp eqExp;

    public void print(){
        if(eqExp!=null){
            eqExp.print();
            eqOp.print();
        }
        relExp.print();
        myWriter.writeNonTerminal("EqExp");
    }

    @Override
    public void visit() {
        if(eqExp!=null){
            eqExp.visit();
            Value a= zext(Visitor.upValue);int leftConst = Visitor.upConstValue;
            relExp.visit();
            Value b= zext(Visitor.upValue);int rightConst = Visitor.upConstValue;
            if (a instanceof ConstInt && b instanceof ConstInt) {
                // 常量折叠优化
                if (eqOp.type() == tokenType.EQL) {
                    Visitor.upConstValue = (leftConst == rightConst) ? 1 : 0;
                } else {
                    Visitor.upConstValue = (leftConst != rightConst) ? 1 : 0;
                }

                Visitor.upValue = new ConstInt(IntegerType.i1, Visitor.upConstValue);
            } else if(eqOp.type()== tokenType.EQL){
                icmp ICMP=new icmp(icmp.EQ,a,b);
                Visitor.curBlock.addInstruction(ICMP);
                Visitor.upValue=ICMP;
            } else if (eqOp.type()==tokenType.NEQ) {
                icmp ICMP=new icmp(icmp.NE,a,b);
                Visitor.curBlock.addInstruction(ICMP);
                Visitor.upValue=ICMP;
            }

        }else{
            relExp.visit();
        }
    }
}//相等性表达式
