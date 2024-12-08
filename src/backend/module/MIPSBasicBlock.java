package backend.module;

import backend.MIPSInstructions.*;
import backend.operand.*;
import frontend.llvm_ir.*;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.BinaryOperations.BinaryOperation;
import frontend.llvm_ir.instructions.ControlFlowInstructions.br;
import frontend.llvm_ir.instructions.ControlFlowInstructions.ret;
import frontend.llvm_ir.instructions.Instruction;
import frontend.llvm_ir.instructions.MemInstructions.alloca;
import frontend.llvm_ir.instructions.MemInstructions.getelementptr;
import frontend.llvm_ir.instructions.MemInstructions.load;
import frontend.llvm_ir.instructions.MemInstructions.store;
import frontend.llvm_ir.instructions.MixedInstructions.call;
import frontend.llvm_ir.type.ArrayType;
import frontend.llvm_ir.type.PointerType;
import frontend.llvm_ir.type.VoidType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MIPSBasicBlock {

    // 基本块的标签
    private final String label;
    // 存储 MIPS 指令的列表
    private final LinkedList<MIPSInstruction> instructions;
    private final Function function;

    private final MIPSFunction mipsFunction;

    // 构造函数
    public MIPSBasicBlock(String label, Function function, MIPSFunction mipsFunction) {
        this.label = label;
        this.instructions = new LinkedList<>();
        this.function = function;
        this.mipsFunction = mipsFunction;
    }

    public LinkedList<MIPSInstruction> getInstructions() {
        return instructions;
    }


    private Reg allocate() {
        for (PhysicalReg reg : PhysicalReg.regs) {
            if (reg.isFree()) {
                reg.allocate();
                return reg;
            }
        }
        return null;
    }

    private void release(MIPSInstruction instruction) {
        for (Reg reg : instruction.getUses()) {
            if (!instruction.getDefs().contains(reg)) {
                reg.release();
                Value value = MIPSModel.getVReg2Value().get(reg);
                MIPSModel.getValue2VReg().remove(value);
            }
        }
    }

    /**
     * @param value 传入llvm的变量或者常量
     * @return 虚拟寄存器
     */
    private Operand getVReg(Value value) {
        Reg VReg;
        if (value instanceof ConstInt constInt) {
            VReg = allocate();
//            VReg = new VirtualReg();
            addInstruction(new MIPSMove(VReg, new Immediate(constInt.getValue())));
        } else {
            VReg = allocVReg(value);
        }
        return VReg;
    }

    //分配新的虚拟寄存器
    private Reg allocVReg(Value value) {
        Reg reg = MIPSModel.getValue2VReg().get(value);
        if (reg == null) reg = allocate();
//       VirtualReg reg = new VirtualReg();
        MIPSModel.getVReg2Value().put(reg, value);
        MIPSModel.getValue2VReg().put(value, reg);
        return reg;
    }

    // 添加一条 MIPS 指令
    public void addInstruction(MIPSInstruction instruction) {
        instructions.add(instruction);
        release(instruction);
    }

    public void buildBasicBlock(BasicBlock basicBlock) {
        for (Instruction instruction : basicBlock.getInstructions()) {

            // 根据 LLVM 指令类型调用相应的翻译函数
            if (instruction instanceof BinaryOperation binaryOperation) {
                transBinaryOperation(binaryOperation);
            } else if (instruction instanceof ret ret) {
                transRet(ret, basicBlock);
            } else if (instruction instanceof br br) {
                transBr(br);
            } else if (instruction instanceof call call) {
                transCall(call);
            } else if (instruction instanceof alloca alloca) {
                transAlloca(alloca);//申请栈空间
            } else if (instruction instanceof getelementptr getElementPtr) {
                transGetelementptr(getElementPtr);
            } else if (instruction instanceof load load) {
                transLoad(load);
            } else if (instruction instanceof store store) {
                transStore(store);
            } else {//对于trunc和zext
                MIPSBinary and = new MIPSBinary("andi", getVReg(instruction), getVReg(instruction.getOperand(0)), new Label("0x00ff"));
                addInstruction(and);
//                Reg VReg = MIPSModel.getValue2VReg().get(instruction.getOperand(0));
//                MIPSModel.getValue2VReg().put(instruction, VReg);
            }
        }
    }


    private void transBinaryOperation(BinaryOperation binaryOperation) {
        //给结果分配虚拟寄存器，如果左操作数是constInt，先调用li指令，如果右操作数是constInt，则得到立即数
        Operand vReg1 = getVReg(binaryOperation);
        Operand vReg2 = getVReg(binaryOperation.getOp1());
        Operand vReg3 = binaryOperation.getOp2() instanceof ConstInt constInt ? new Immediate(constInt.getValue()) : getVReg(binaryOperation.getOp2());

        MIPSBinary binary = new MIPSBinary(binaryOperation.getMipsType(), vReg1, vReg2, vReg3);
        addInstruction(binary);

    }


    private void transRet(ret ret, BasicBlock basicBlock) {
        if (((Function) basicBlock.getParent()).isMainFunc()) {
            if (mipsFunction.stackTop != 0) {
                MIPSBinary binary = new MIPSBinary("addiu", PhysicalReg.$sp, PhysicalReg.$sp, new Immediate(mipsFunction.stackTop));
                addInstruction(binary);
            }
            MIPSMacro macro = new MIPSMacro("li $v0, 10\n\tsyscall");
            addInstruction(macro);
        } else {
            if (!ret.isVoidType()) {
                Value retValue = ret.getOperand(0);
                MIPSMove mipsMove = new MIPSMove(PhysicalReg.$v0, getVReg(retValue));
                addInstruction(mipsMove);
            }
            if (mipsFunction.stackTop != 0) {
                MIPSBinary binary = new MIPSBinary("addiu", PhysicalReg.$sp, PhysicalReg.$sp, new Immediate(mipsFunction.stackTop));
                addInstruction(binary);
            }
            MIPSJump jump = new MIPSJump("jr", PhysicalReg.$ra);
            addInstruction(jump);
        }
    }

    private void transBr(br br) {
        if (br.isJmp()) {
            MIPSJump jump = new MIPSJump("j", new Label(br.getLabel()));
            addInstruction(jump);

        } else {
            Operand cond = getVReg(br.getCondition());
            MIPSJump jump = new MIPSJump("bnez", cond, new Label(br.getTrueLabel()));
            addInstruction(jump);

            jump = new MIPSJump("j", new Label(br.getFalseLabel()));
            addInstruction(jump);

        }
    }


    private void transCall(call call) {
        String funcName = call.getFunction().getName().substring(1);
        if (call.isExternal()) {
            // 处理外部函数调用
            if (funcName.equals("putint") || funcName.equals("putch") || funcName.equals("putstr")) {
                MIPSMove move = new MIPSMove(PhysicalReg.$a0, getVReg(call.getOperand(1)));
                addInstruction(move);

            }
            MIPSMacro macro = new MIPSMacro(funcName);
            addInstruction(macro);
            if (!(call.getFunction().getType() instanceof VoidType) && !call.getUsers().isEmpty()) {
                Operand returnReg = getVReg(call);
                MIPSInstruction moveReturn = new MIPSMove(returnReg, PhysicalReg.$v0);
                addInstruction(moveReturn);
            }
        } else {
            List<Value> args = call.getOperands().subList(1, call.getOperands().size());
            // 计算需要为参数和返回地址预留的栈空间
            int size = (int) args.stream().filter(arg -> !MIPSModel.getValue2VReg().containsKey(arg)).count() + MIPSModel.getValue2VReg().size();
            int stackSpace = size * 4 + 4;
            mipsFunction.stackTop += stackSpace;
            // 调整栈指针，分配栈空间
            MIPSBinary binary = new MIPSBinary("addiu", PhysicalReg.$sp, PhysicalReg.$sp, new Immediate(-stackSpace));
            addInstruction(binary);
            // 保存参数到栈中
            int i;
            for (i = 0; i < args.size(); i++) {
                Value arg = args.get(i);
                MIPSStore store = new MIPSStore(getVReg(arg), new Immediate(i * 4), PhysicalReg.$sp);
                addInstruction(store);
            }
            //保存用到的临时变量寄存器到栈中
            ArrayList<Reg> regs = new ArrayList<>(MIPSModel.getValue2VReg().values());
            for (Reg reg : regs) {
                MIPSStore sw = new MIPSStore(reg, new Immediate(i * 4), PhysicalReg.$sp);
                addInstruction(sw);
                i++;
            }
//            // 保存返回地址到栈上
            MIPSInstruction swRa = new MIPSStore(PhysicalReg.$ra, new Immediate(size * 4), PhysicalReg.$sp);
            addInstruction(swRa);
            // 使用 `jal` 指令跳转到被调用函数，并保存返回地址到 $ra
            MIPSJump jal = new MIPSJump("jal", new Label(funcName));
            addInstruction(jal);
            // 从栈上加载回 $ra和用到的临时变量寄存器
            MIPSInstruction loadRa = new MIPSLoad(PhysicalReg.$ra, new Immediate(size * 4), PhysicalReg.$sp);
            addInstruction(loadRa);
            Collections.reverse(regs);
            for (Reg reg : regs) {
                i--;
                MIPSLoad lw = new MIPSLoad(reg, new Immediate(i * 4), PhysicalReg.$sp);
                addInstruction(lw);
                ((PhysicalReg) reg).allocate();
                MIPSModel.Value2VReg.put(MIPSModel.VReg2Value.get(reg), reg);
            }
            // 恢复栈指针，释放栈空间
            binary = new MIPSBinary("addiu", PhysicalReg.$sp, PhysicalReg.$sp, new Immediate(stackSpace));
            addInstruction(binary);
            mipsFunction.stackTop -= stackSpace;
            // 处理被调用的函数返回值
            if (!(call.getFunction().getType() instanceof VoidType) && !call.getUsers().isEmpty()) {
                Operand returnReg = getVReg(call);
                MIPSInstruction moveReturn = new MIPSMove(returnReg, PhysicalReg.$v0);
                addInstruction(moveReturn);
            }
        }
    }

    //gep 指令的基址类型（全局数组、局部数组);
    private void transGetelementptr(getelementptr getelementptr) {

        //计算offset
        Operand offset;
        if (((PointerType) getelementptr.getPtrval().getType()).getPointedType() instanceof ArrayType) {
            offset = getVReg(getelementptr.getOperand(1));
        } else {
            offset = getVReg(getelementptr.getOperand(0));
        }
        MIPSBinary binary = new MIPSBinary("sll", offset, offset, new Immediate(2));
        addInstruction(binary);


        Operand base = getVReg(getelementptr.getPtrval());
        if (getelementptr.getPtrval() instanceof GlobalVar globalVar) {//la
            MIPSMove la = new MIPSMove(base, new Label(globalVar.getName().substring(1)));
            addInstruction(la);
        } else if (MIPSModel.getValue2Stack().containsKey(getelementptr.getPtrval())) {//如果是alloca出来的栈空间，先用栈获取对应的地址存到base中
            binary = new MIPSBinary("addiu", base, PhysicalReg.$sp, new Immediate(MIPSModel.getValue2Stack().get(getelementptr.getPtrval())));
            addInstruction(binary);
        }

        binary = new MIPSBinary("addu", getVReg(getelementptr), base, offset);
        addInstruction(binary);
    }

    //分配新的栈空间
    private void transAlloca(alloca alloca) {
        int offset = 0;
        if (((PointerType) alloca.getType()).getPointedType() instanceof ArrayType arrayType)
            offset = 4 * arrayType.getArraySize();
        else offset += 4;

//        //分配栈空间
        MIPSModel.getValue2Stack().put(alloca, mipsFunction.stackTop);//记录当前栈顶
        mipsFunction.stackTop += offset;
    }

    private void transLoad(load load) {
        Value addr = load.getOperand(0);
        if (addr instanceof GlobalVar globalVar) {
            MIPSLoad lw = new MIPSLoad(getVReg(load), new Label(globalVar.getName().substring(1)), PhysicalReg.$zero); // 偏移量为0
            addInstruction(lw);
        } else {
            if (MIPSModel.getValue2Stack().containsKey(addr)) {//如果存在栈里面
                Operand offset = new Immediate(MIPSModel.getValue2Stack().get(addr));
                MIPSLoad lw = new MIPSLoad(getVReg(load), offset, PhysicalReg.$sp);
                addInstruction(lw);
            } else {
                MIPSLoad lw = new MIPSLoad(getVReg(load), Immediate.zero, getVReg(addr)); // 偏移量为0
                addInstruction(lw);
            }
        }
    }

    private void transStore(store store) {
        Value value = store.getOperand(0);
        Value addr = store.getOperand(1);
        if (addr instanceof GlobalVar globalVar) {//如果是全局
            MIPSStore sw = new MIPSStore(getVReg(value), new Label(globalVar.getName().substring(1)), PhysicalReg.$zero);
            addInstruction(sw);

        } else {
            if (value instanceof Parameter parameter) {//如果是函数参数
                Operand offset = new Immediate((function.getAllocaNum() + parameter.getParamIndex()) * 4);
                MIPSLoad lw = new MIPSLoad(getVReg(value), offset, PhysicalReg.$sp);
                addInstruction(lw);
            }

            if (MIPSModel.getValue2Stack().containsKey(addr)) {
                Operand offset = new Immediate(MIPSModel.getValue2Stack().get(addr));
                MIPSStore sw = new MIPSStore(getVReg(value), offset, PhysicalReg.$sp);
                addInstruction(sw);
            } else {
                MIPSStore sw = new MIPSStore(getVReg(value), Immediate.zero, getVReg(addr));
                addInstruction(sw);

            }
        }
    }

    // 生成基本块的 MIPS 代码
    public String mips() {
        StringBuilder sb = new StringBuilder();

        // 添加基本块标签
        sb.append(label).append(":\n");

        // 添加每条指令
        for (MIPSInstruction instruction : instructions) {
            sb.append("\t").append(instruction.mips()).append("\n");
        }

        return sb.toString();
    }

}

