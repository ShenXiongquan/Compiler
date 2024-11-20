package frontend.node.primaryExp;


import frontend.Visitor;
import frontend.ir.instructions.MemInstructions.load;
import frontend.ir.type.PointerType;
import frontend.node.LVal;
import frontend.tool.myWriter;

public class LValPE extends PrimaryExp {
    public LVal lVal;

    @Override
    public void print() {
        lVal.print();
        myWriter.writeNonTerminal("PrimaryExp");
    }

    @Override
    public void visit() {
        lVal.visit();

        if(!Visitor.lValNotLoad&&(Visitor.upValue.getType() instanceof PointerType)){
            load load=new load(Visitor.upValue);
            Visitor.curBlock.addInstruction(load);
            Visitor.upValue=load;
        }
    }
}
