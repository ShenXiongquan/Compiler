package frontend.node;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.BinaryOperations.icmp;
import frontend.llvm_ir.type.IntegerType;
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

    public void visit() {
        if(eqExp!=null){
            eqExp.visit();
            Value lhs= zext(Visitor.upValue);int lConst = Visitor.upConstValue;
            relExp.visit();
            Value rhs= zext(Visitor.upValue);int rConst = Visitor.upConstValue;
            if (lhs instanceof ConstInt && rhs instanceof ConstInt) {// 处理常量比较
                Visitor.upConstValue = switch (eqOp.type()) {
                    case EQL -> (lConst == rConst) ? 1 : 0;
                    case NEQ -> (lConst != rConst) ? 1 : 0;
                    default -> throw new IllegalArgumentException("Unexpected tokenType: " + eqOp.type());
                };
                Visitor.upValue = new ConstInt(IntegerType.i1, Visitor.upConstValue);
            } else {// 处理非常量比较
                String icmpType = switch (eqOp.type()) {
                    case EQL -> icmp.EQ;
                    case NEQ -> icmp.NE;
                    default -> throw new IllegalArgumentException("Unexpected tokenType: " + eqOp.type());
                };
                Visitor.upValue = icmp(icmpType, lhs, rhs);
            }
        }else{
            relExp.visit();
        }
    }
}//相等性表达式
