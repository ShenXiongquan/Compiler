package frontend.node;


import frontend.Visitor;
import frontend.ir.Parameter;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.instructions.MemInstructions.load;
import frontend.ir.instructions.MixedInstructions.trunc;
import frontend.ir.instructions.MixedInstructions.zext;
import frontend.ir.type.IntegerType;
import frontend.ir.type.PointerType;

import java.util.ArrayList;


public abstract class node {

    public abstract void visit();

    //对于zext和trunc，有三种类型，一种是常量，一种是变量，一种是指针
    protected Value zext(Value value) {
        System.out.println("可能需要转化的类型:"+value.getType().ir());
        if (value instanceof ConstInt constInt) {
            value = new ConstInt(IntegerType.i32, constInt.getValue());
        } else if (value.getType() instanceof PointerType) {
            load load = new load(value);
            Visitor.curBlock.addInstruction(load);
            System.out.println("load的类型:"+load.getType().ir());
            value=load;
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
            load load = new load(value);
            Visitor.curBlock.addInstruction(load);
            value=load;
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


}
