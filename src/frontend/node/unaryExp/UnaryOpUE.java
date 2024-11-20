package frontend.node.unaryExp;

import frontend.Visitor;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.instructions.BinaryOperations.icmp;
import frontend.ir.instructions.BinaryOperations.sub;
import frontend.ir.type.IntegerType;
import frontend.node.UnaryOp;
import frontend.token.tokenType;
import frontend.tool.myWriter;

public class UnaryOpUE extends UnaryExp {
    public UnaryOp unaryOp;
    public UnaryExp unaryExp;

    @Override
    public void print() {
        unaryOp.print();
        unaryExp.print();
        myWriter.writeNonTerminal("UnaryExp");
    }

    @Override
    public void visit() {
        unaryExp.visit();
        if (Visitor.calAble) {
            if (unaryOp.op.type() == tokenType.MINU) {
                Visitor.upConstValue = -Visitor.upConstValue;
            } else if (unaryOp.op.type() == tokenType.NOT) {
                Visitor.upConstValue = Visitor.upConstValue == 0 ? 1 : 0;
            }
        } else {
            if (unaryOp.op.type() == tokenType.MINU) {
                Value op1 = ConstInt.zero;
                Value op2 = zext(Visitor.upValue);
                sub sub = new sub(op1, op2);
                Visitor.curBlock.addInstruction(sub);
                Visitor.upValue = sub;
            } else if (unaryOp.op.type() == tokenType.NOT) {
                icmp icmp=new icmp(frontend.ir.instructions.BinaryOperations.icmp.EQ,Visitor.upValue,new ConstInt((IntegerType) Visitor.upValue.getType(),0));
                Visitor.curBlock.addInstruction(icmp);
                Visitor.upValue=icmp;
            }
        }

    }
}
