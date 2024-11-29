package frontend.node;


import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.BinaryOperations.*;
import frontend.llvm_ir.instructions.ControlFlowInstructions.br;
import frontend.llvm_ir.instructions.ControlFlowInstructions.ret;
import frontend.llvm_ir.instructions.MemInstructions.alloca;
import frontend.llvm_ir.instructions.MemInstructions.getelementptr;
import frontend.llvm_ir.instructions.MemInstructions.load;
import frontend.llvm_ir.instructions.MemInstructions.store;
import frontend.llvm_ir.instructions.MixedInstructions.call;
import frontend.llvm_ir.instructions.MixedInstructions.trunc;
import frontend.llvm_ir.instructions.MixedInstructions.zext;
import frontend.llvm_ir.type.IntegerType;
import frontend.llvm_ir.type.PointerType;
import frontend.llvm_ir.type.Type;


public abstract class node {
    public abstract String print();

    //对于zext和trunc，封装的较为复杂
    protected Value zext(Value value) {
        if (value instanceof ConstInt constInt) {
            value = new ConstInt(IntegerType.i32, constInt.getValue());
        } else if (value.getType() instanceof PointerType) {
            value = load(value);
            if (!value.getType().isInt32()) {
                zext zext = new zext(value);
                Visitor.curBlock.addInstruction(zext);
                value = zext;
            }
        } else if (!value.getType().isInt32()) {
            zext zext = new zext(value);
            Visitor.curBlock.addInstruction(zext);
            value = zext;
        }

        return value;
    }

    protected Value trunc(Value value) {
        if (value instanceof ConstInt constInt) {
            value = new ConstInt(IntegerType.i8, constInt.getValue());
        } else if (value.getType() instanceof PointerType) {
            // 对指针类型先加载值
            value = load(value);
            // 再进行 trunc 操作
            if (value.getType().isInt32()) {
                trunc trunc = new trunc(value);
                Visitor.curBlock.addInstruction(trunc);
                value = trunc;
            }
        } else if (value.getType().isInt32()) {
            // 如果类型是 i32，并且不是常量，则插入 trunc 指令
            trunc trunc = new trunc(value);
            Visitor.curBlock.addInstruction(trunc);
            value = trunc;
        }
        return value;
    }
    // 封装二元操作指令


//    protected and and(Value lhs, Value rhs) {
//        and andInstruction = new and(lhs, rhs);
//        Visitor.curBlock.addInstruction(andInstruction);
//        return andInstruction;
//    }

    //    protected or or(Value lhs, Value rhs) {
//        or orInstruction = new or(lhs, rhs);
//        Visitor.curBlock.addInstruction(orInstruction);
//        return orInstruction;
//    }
    protected add add(Value lhs, Value rhs) {
        add addInstruction = new add(lhs, rhs);
        Visitor.curBlock.addInstruction(addInstruction);
        return addInstruction;
    }

    protected mul mul(Value lhs, Value rhs) {
        mul mulInstruction = new mul(lhs, rhs);
        Visitor.curBlock.addInstruction(mulInstruction);
        return mulInstruction;
    }


    protected sdiv sdiv(Value lhs, Value rhs) {
        sdiv sdivInstruction = new sdiv(lhs, rhs);
        Visitor.curBlock.addInstruction(sdivInstruction);
        return sdivInstruction;
    }

    protected srem srem(Value lhs, Value rhs) {
        srem sremInstruction = new srem(lhs, rhs);
        Visitor.curBlock.addInstruction(sremInstruction);
        return sremInstruction;
    }

    protected sub sub(Value lhs, Value rhs) {
        sub subInstruction = new sub(lhs, rhs);
        Visitor.curBlock.addInstruction(subInstruction);
        return subInstruction;
    }

    // 2. 封装 icmp 指令
    protected icmp icmp(String condition, Value lhs, Value rhs) {
        icmp icmpInstruction = new icmp(condition, lhs, rhs);
        Visitor.curBlock.addInstruction(icmpInstruction);
        return icmpInstruction;
    }

    // 封装内存操作指令
    protected alloca alloca(Type type) {
        alloca allocaInstruction = new alloca(type);
        Visitor.curBlock.addInstruction(allocaInstruction);
        return allocaInstruction;
    }

    protected getelementptr getelementptr(Value base, Value offset) {
        getelementptr gepInstruction = new getelementptr(base, offset);
        Visitor.curBlock.addInstruction(gepInstruction);
        return gepInstruction;
    }

    protected getelementptr getelementptr(Value base, Value offset1, Value offset2) {
        getelementptr gepInstruction = new getelementptr(base, offset1, offset2);
        Visitor.curBlock.addInstruction(gepInstruction);
        return gepInstruction;
    }

    protected void store(Value value, Value pointer) {
        store storeInstruction = new store(value, pointer);
        Visitor.curBlock.addInstruction(storeInstruction);
    }

    protected load load(Value pointer) {
        load loadInstruction = new load(pointer);
        Visitor.curBlock.addInstruction(loadInstruction);
        return loadInstruction;
    }

    protected call call(Function function, Value... args) {
        call callInstruction = new call(function, args);
        Visitor.curBlock.addInstruction(callInstruction);
        return callInstruction;
    }

    // （无条件跳转）
    protected void br(BasicBlock targetBlock) {
        br brInstruction = new br(targetBlock);
        Visitor.curBlock.addInstruction(brInstruction);
    }

    // （条件跳转）
    protected void br(Value condition, BasicBlock trueBlock, BasicBlock falseBlock) {
        br brInstruction = new br(condition, trueBlock, falseBlock);
        Visitor.curBlock.addInstruction(brInstruction);
    }

    // ret
    protected void ret(Value returnValue) {
        ret retInstruction = new ret(returnValue);
        Visitor.curBlock.addInstruction(retInstruction);
    }

    protected void ret() {
        ret retInstruction = new ret();
        Visitor.curBlock.addInstruction(retInstruction);
    }

    // 进入新基本块
    protected void enterNewBlock(BasicBlock block) {
        Visitor.curBlock = block;
        Visitor.curFunc.addBasicBlock(block);
    }

}
