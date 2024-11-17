package frontend.node.primaryExp;

import frontend.Visitor;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.instructions.MemInstructions.load;
import frontend.ir.type.IntegerType;
import frontend.node.LVal;
import frontend.tool.myWriter;

public class LValPE extends PrimaryExp{
    public LVal lVal;

    @Override
    public void print() {
        lVal.print();
        myWriter.writeNonTerminal("PrimaryExp");
    }

    @Override
    public void visit() {
        lVal.visit();
        if (!(Visitor.upValue.getType() instanceof IntegerType)) {
            load load=new load(Visitor.upValue,Visitor.curBlock);
            Visitor.curBlock.addInstruction(load);
        }

    }
}
