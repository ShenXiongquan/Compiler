package frontend.llvm_ir.instructions.BinaryOperations;

import frontend.llvm_ir.Function;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.instructions.Instruction;
import frontend.llvm_ir.type.Type;

import java.util.ArrayList;


public abstract class BinaryOperation extends Instruction {

    /**
     * @param op1 第一个操作数
     * @param op2 第二个操作数
     */
    public BinaryOperation(Type returnType, Value op1, Value op2) {
        super(LOCAL_PREFIX + (Function.VarNum++), returnType, new ArrayList<>(){{
            add(op1);add(op2);
        }});
    }
    public Value getOp1(){
        return getOperand(0);
    }
    public Value getOp2(){
        return getOperand(1);
    }
}
