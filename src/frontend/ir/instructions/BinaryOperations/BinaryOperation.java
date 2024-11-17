package frontend.ir.instructions.BinaryOperations;

import frontend.ir.BasicBlock;
import frontend.ir.Value;
import frontend.ir.instructions.Instruction;
import frontend.ir.type.Type;

import java.util.ArrayList;


public abstract class BinaryOperation extends Instruction {

    /**
     * @param op1 第一个操作数
     * @param op2 第二个操作数
     */
    public BinaryOperation(Type returnType, Value op1, Value op2, BasicBlock parent) {
        super(Value.LOCAL_PREFIX + (Value.VarNum++), returnType, new ArrayList<>(){{
            add(op1);add(op2);
        }},parent);
    }
    public Value getOp1(){
        return getOperand(0);
    }
    public Value getOp2(){
        return getOperand(1);
    }
}
