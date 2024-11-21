package frontend.node;


import frontend.Visitor;
import frontend.ir.Value;
import frontend.ir.instructions.MixedInstructions.trunc;
import frontend.ir.instructions.MixedInstructions.zext;


public abstract class node {

    public abstract void visit();

    protected Value zext(Value value) {
        if (!value.getType().isInt32()) {
            zext zext = new zext(value);
            Visitor.curBlock.addInstruction(zext);
            value = zext;
        }
        return value;
    }

    protected Value trunc(Value value) {

        if (value.getType().isInt32()) {
            trunc trunc = new trunc(value);
            Visitor.curBlock.addInstruction(trunc);
            value = trunc;
        }
        return value;
    }

}
