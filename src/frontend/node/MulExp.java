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
        if(Visitor.calAble){
            if(mulExp!=null){
                mulExp.visit();
                int a=Visitor.upConstValue;
                unaryExp.visit();
                int b=Visitor.upConstValue;
                if(op.type()== tokenType.MULT){
                    Visitor.upConstValue =a*b;
                }else if(op.type()==tokenType.DIV){
                    Visitor.upConstValue =a/b;

                }else {
                    Visitor.upConstValue =a%b;
                }
                System.out.println("mul result:"+Visitor.upConstValue);
            }else{
                unaryExp.visit();
            }
        }else{
            if(mulExp!=null){
                mulExp.visit();
                Value a=Visitor.upValue;
                unaryExp.visit();
                Value b=Visitor.upValue;
                if(op.type()== tokenType.MULT){
                    Visitor.curBlock.addInstruction(new mul( a,b,Visitor.curBlock));
                }else if(op.type()==tokenType.DIV){
                    Visitor.curBlock.addInstruction(new sdiv( a,b,Visitor.curBlock));
                }else {
                    Visitor.curBlock.addInstruction(new srem( a,b,Visitor.curBlock));
                }
            }else{
                unaryExp.visit();
            }
        }
    }
}//乘除模表达式
