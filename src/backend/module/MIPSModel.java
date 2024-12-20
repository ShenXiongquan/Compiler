package backend.module;

//model包含globalvar和Function，Function包含BasicBlock，BasicBlock包含instruction，instrcution中有operand

import backend.operand.Reg;
import frontend.llvm_ir.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MIPSModel {

    final static LinkedHashMap<Value, Reg> Value2VReg = new LinkedHashMap<>();
    final static HashMap<Reg, Value> VReg2Value = new HashMap<>();
    final static HashMap<Value, Integer> Value2Stack = new HashMap<>();
    // 全局变量列表
    private static final List<MIPSGlobalVar> globalVars = new ArrayList<>();
    // 函数列表
    private static final List<MIPSFunction> functions = new ArrayList<>();

    // 主函数
    public static LinkedHashMap<Value, Reg> getValue2VReg() {
        return Value2VReg;
    }

    public static HashMap<Reg, Value> getVReg2Value() {
        return VReg2Value;
    }

    public static HashMap<Value, Integer> getValue2Stack() {
        return Value2Stack;
    }

    // 添加全局变量
    public void addGlobalVar(MIPSGlobalVar globalVar) {
        globalVars.add(globalVar);
    }

    // 添加函数
    public void addFunction(MIPSFunction function) {
        functions.add(function);
    }

    public void buildModel(Model irModel) {
        for (GlobalValue globalValue : irModel.getGlobalValues()) {
            if (globalValue instanceof GlobalVar globalVar) {//全局变量
                MIPSGlobalVar mipsGlobalVar = new MIPSGlobalVar(globalVar.getName().substring(1), globalVar.getInitial());
                addGlobalVar(mipsGlobalVar);
            } else if (globalValue instanceof Function function && function.isDefine()) {//函数声明
                MIPSFunction mipsFunction = new MIPSFunction(function.getMipsName(), function);
                addFunction(mipsFunction);
                mipsFunction.buildFunction();
            }
        }

    }

    public String mips() {
        StringBuilder sb = new StringBuilder();
        //输入输出的.marco段
        sb.append(".macro getint\n");
        sb.append("\tli $v0, 5\n");
        sb.append("\tsyscall\n");
        sb.append(".end_macro\n\n");

        sb.append(".macro getchar\n");
        sb.append("\tli $v0, 12\n");
        sb.append("\tsyscall\n");
        sb.append(".end_macro\n\n");

        sb.append(".macro putint\n");
        sb.append("\tli $v0, 1\n");
        sb.append("\tsyscall\n");
        sb.append(".end_macro\n\n");

        sb.append(".macro putch\n");
        sb.append("\tli $v0, 11\n");
        sb.append("\tsyscall\n");
        sb.append(".end_macro\n\n");

        sb.append(".macro putstr\n");
        sb.append("\tli $v0, 4\n");
        sb.append("\tsyscall\n");
        sb.append(".end_macro\n\n");
        //.data段
        sb.append(".data\n");
        for (MIPSGlobalVar mipsGlobalVar : globalVars) {
            sb.append(mipsGlobalVar.mips());
        }
        //.text段
        sb.append(".text\n");
//        sb.append(".globl f_main\n");


        for (int i = functions.size() - 1; i >= 0; i--) { // 从最后一个元素开始遍历
            MIPSFunction function = functions.get(i);
            sb.append(function.mips()).append("\n");
        }

        return sb.toString();
    }

}
