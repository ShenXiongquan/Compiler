package backend;

//model包含globalvar和Function，Function包含BasicBlock，BasicBlock包含instruction，instrcution中有operand

import frontend.llvm_ir.Function;
import frontend.llvm_ir.GlobalValue;
import frontend.llvm_ir.GlobalVar;
import frontend.llvm_ir.Model;

import java.util.ArrayList;
import java.util.List;

public class MIPSModel {

    // 全局变量列表
    private final List<MIPSGlobalVar> globalVars;
    // 函数列表
    private final List<MIPSFunction> functions;
    // 主函数
    private MIPSFunction mainFunction;

    public MIPSModel() {
        this.globalVars = new ArrayList<>();
        this.functions = new ArrayList<>();
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
                MIPSGlobalVar mipsGlobalVar = new MIPSGlobalVar(globalVar.getName(), globalVar.getInitial());
                addGlobalVar(mipsGlobalVar);
            } else if (globalValue instanceof Function function) {//函数声明
                MIPSFunction mipsFunction = new MIPSFunction(function.getName());
                addFunction(mipsFunction);
                mipsFunction.buildFunction(function);
            }
        }
    }

    public String mips() {
        StringBuilder sb = new StringBuilder();
        //输入输出的.marco段
        int[] syscallValues = {5, 12, 1, 11, 4}; // 按顺序存储需要的 $v0 值
        for (int i = 0; i < 5; i++) {
            MIPSFunction macro = functions.get(i);
            sb.append(".macro ").append(macro.getName()).append("\n");
            sb.append("\tli $v0, ").append(syscallValues[i]).append("\n");
            sb.append("\tsyscall\n");
            sb.append(".end_macro\n\n");
        }
        //.data段
        sb.append(".data\n");
        for (MIPSGlobalVar mipsGlobalVar : globalVars) {
            sb.append(mipsGlobalVar.mips());
        }
        //.text段
        sb.append(".text\n");
        sb.append("\t.globl main\n");
        for (int i = 5; i < functions.size(); i++) {
            MIPSFunction function = functions.get(i);
            sb.append(function.mips()).append("\n");
        }

        return sb.toString();
    }

}
