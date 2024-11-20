package frontend.node;

import frontend.Visitor;
import frontend.ir.Value;
import frontend.ir.instructions.BinaryOperations.*;
import frontend.node.unaryExp.UnaryExp;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.myWriter;

public class MulExp extends node {

    public MulExp mulExp;
    public token op;
    public UnaryExp unaryExp;

    public void print() {
        if (mulExp != null) {
            mulExp.print();
            op.print();
        }
        unaryExp.print();
        myWriter.writeNonTerminal("MulExp");
    }

    @Override
    public void visit() {
        if (Visitor.calAble) {
            if (mulExp != null) {
                mulExp.visit();
                int a = Visitor.upConstValue;
                unaryExp.visit();
                int b = Visitor.upConstValue;
                if (op.type() == tokenType.MULT) {
                    Visitor.upConstValue = a * b;
                } else if (op.type() == tokenType.DIV) {
                    Visitor.upConstValue = a / b;

                } else {
                    Visitor.upConstValue = a % b;
                }
                System.out.println("mul result:" + Visitor.upConstValue);
            } else {
                unaryExp.visit();
            }
        } else {
            if (mulExp != null) {
                mulExp.visit();
                Value a = zext(Visitor.upValue);
                unaryExp.visit();
                Value b = zext(Visitor.upValue);
                if (op.type() == tokenType.MULT) {
                    mul mul = new mul(a, b);
                    Visitor.curBlock.addInstruction(mul);
                    Visitor.upValue = mul;
                } else if (op.type() == tokenType.DIV) {
                    sdiv sdiv = new sdiv(a, b);
                    Visitor.curBlock.addInstruction(sdiv);
                    Visitor.upValue = sdiv;
                } else {
                    srem srem = new srem(a, b);
                    Visitor.curBlock.addInstruction(srem);
                    Visitor.upValue = srem;
                }
            } else {
                unaryExp.visit();
            }
        }
    }
}//乘除模表达式
