package frontend.node.stmt;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.type.IntegerType;
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

    public void visit() {

        if (returnExp == null) {
            ret();
        } else {
            returnExp.visit();
            IntegerType expectedType = (IntegerType) ((Function) Visitor.curBlock.getParent()).getType().getReturnType();
            if (expectedType.isInt32()) Visitor.upValue = zext(Visitor.upValue);
            else Visitor.upValue = trunc(Visitor.upValue);
            ret(Visitor.upValue);
        }
        enterNewBlock(new BasicBlock("Block_return" + Function.returnNum++));

    }
}
