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
        //lval出现在等号左侧的时候，不用考虑加载了，但目前，lval可能是单独存在，也可能是存在于等号右侧，那么就需要考虑加载
        if(!Visitor.lValNotLoad&&(Visitor.upValue.getType() instanceof PointerType)){
            load load=new load(Visitor.upValue);
            Visitor.curBlock.addInstruction(load);
            Visitor.upValue=load;
        }
    }
}
