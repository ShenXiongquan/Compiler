package frontend.node.stmt;

import frontend.Visitor;
import frontend.ir.Function;
import frontend.ir.constants.ConstInt;
import frontend.ir.instructions.ControlFlowInstructions.ret;
import frontend.ir.type.IntegerType;
import frontend.node.Exp;
import frontend.token.token;
import frontend.tool.myWriter;

public class ReturnStmt extends Stmt {
    public token returnToken;
    public Exp returnExp;

    public token semicn;

    @Override
    public void print() {
        returnToken.print();
        if (returnExp != null) returnExp.print();
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("Stmt");
    }
    @Override
    public void visit() {
        ret ret;
        if(returnExp==null){
            ret=new ret();
        }else{
            returnExp.visit();
            IntegerType expectedType= (IntegerType) ((Function)Visitor.curBlock.getParent()).getType().getReturnType();
            if(Visitor.upValue instanceof ConstInt) Visitor.upValue=new ConstInt(expectedType,((ConstInt) Visitor.upValue).getValue());
            else if(expectedType.isInt32())Visitor.upValue=zext(Visitor.upValue);
            else Visitor.upValue=trunc(Visitor.upValue);

            ret=new ret(Visitor.upValue);
        }
        Visitor.curBlock.addInstruction(ret);
    }
}
