package frontend.node;

import frontend.Visitor;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.BinaryOperations.*;
import frontend.llvm_ir.type.IntegerType;
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
                Value a = zext(Visitor.upValue);int l=Visitor.upConstValue;
                unaryExp.visit();Value b = zext(Visitor.upValue);
                int r=Visitor.upConstValue;
                if (a instanceof ConstInt && b instanceof ConstInt) {
                    if (op.type() == tokenType.MULT) Visitor.upConstValue = l * r;
                    else if(op.type()==tokenType.DIV)Visitor.upConstValue = l / r;
                    else Visitor.upConstValue=l%r;
                    Visitor.upValue = new ConstInt(IntegerType.i32, Visitor.upConstValue);
                }else if (op.type() == tokenType.MULT) {
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
