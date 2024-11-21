package frontend.node;

import frontend.Visitor;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.instructions.BinaryOperations.add;
import frontend.ir.instructions.BinaryOperations.sub;
import frontend.ir.type.IntegerType;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.myWriter;

public class AddExp extends node {
    public AddExp addExp;
    public MulExp mulExp;
    public token op;

    public void print() {
        if (addExp != null) {
            addExp.print();
            op.print();
        }
        mulExp.print();
        myWriter.writeNonTerminal("AddExp");
    }

    @Override
    public void visit() {
        if (Visitor.calAble) {
            if (addExp != null) {
                addExp.visit();
                int a = Visitor.upConstValue;
                mulExp.visit();
                int b = Visitor.upConstValue;
                if (op.type() == tokenType.PLUS) Visitor.upConstValue = a + b;
                else Visitor.upConstValue = a - b;
                System.out.println("add result:" + Visitor.upConstValue);
            } else {
                mulExp.visit();
            }
        } else {
            if (addExp != null) {
                addExp.visit();
                Value a = zext(Visitor.upValue);int l = Visitor.upConstValue;
                mulExp.visit();
                Value b = zext(Visitor.upValue);int r = Visitor.upConstValue;
                if (a instanceof ConstInt && b instanceof ConstInt) {

                    if (op.type() == tokenType.PLUS) Visitor.upConstValue = l + r;
                    else Visitor.upConstValue = l - r;
                    Visitor.upValue = new ConstInt(IntegerType.i32, Visitor.upConstValue);
                    System.out.println("结果为:"+Visitor.upConstValue);
                }else if (op.type() == tokenType.PLUS) {

                    add add = new add(a, b);
                    Visitor.curBlock.addInstruction(add);
                    Visitor.upValue = add;
                } else {
                    sub sub = new sub(a, b);
                    Visitor.curBlock.addInstruction(sub);
                    Visitor.upValue = sub;
                }
            } else {
                mulExp.visit();
            }
        }
    }
}//加减表达式
