package frontend.llvm_ir;

import frontend.llvm_ir.type.Type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * 函数，一个函数由若干基本块（BasicBlock）组成，树的第二层
 * declare i32 @getint()
 * declare i32 @getchar()
 * declare void @putint(i32)
 * declare void @putch(i32)
 * declare void @putstr(i8*)
 */
public class Function extends GlobalValue {

    public static int VarNum = 0;
    private final LinkedList<BasicBlock> basicBlocks = new LinkedList<>(); // 函数中的基本块列表
    private final boolean isDefine; // 是否是内建函数
    private final ArrayList<Parameter> parameters; // 函数的形参列表

    private final HashSet<Function> callers = new HashSet<>(); // 调用该函数的其他函数
    private int allocaNum = 0;

    /**
     * @param name       函数名
     * @param returnType 返回类型
     * @param parameters 参数列表
     * @param isDefine   是否为自己定义的
     */
    public Function(String name, Type returnType, ArrayList<Parameter> parameters, boolean isDefine) {
        super(name, returnType);
        this.parameters = parameters;
        this.isDefine = isDefine;
    }

    public void addAllocaNum(int i) {
        allocaNum += i;
    }

    /**
     * @return 需要分配栈的大小/4
     */
    public int getAllocaNum() {
        return allocaNum;
    }

    public HashSet<Function> getCallers() {
        return callers;
    }

    public LinkedList<BasicBlock> getBasicBlocks() {
        return basicBlocks;
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public void addBasicBlock(BasicBlock block) {
        basicBlocks.add(block);
    }

    public void removeBasicBlock(BasicBlock block) {
        basicBlocks.remove(block);
    }

    public boolean isDefine() {
        return isDefine;
    }

    public boolean isMainFunc() {
        return getName().equals("@main");
    }

    public String getMipsName() {
        return "f_" + getName().substring(1);
    }

    //define dso_local i32 @main(){}
    //declare i32 @getint()
    @Override
    public String ir() {
        StringBuilder sb = new StringBuilder(isDefine ? "define dso_local " : "declare ");
        //函数声明
        sb.append(getType().ir()).append(" ").append(getName()).append("(");
        if (!parameters.isEmpty()) {
            sb.append(isDefine ? parameters.get(0).ir() : parameters.get(0).getType().ir());
            for (int i = 1; i < parameters.size(); i++) {
                sb.append(", ");
                sb.append(isDefine ? parameters.get(i).ir() : parameters.get(i).getType().ir());
            }
        }
        sb.append(")");
        //函数体
        if (isDefine) {
            sb.append("{\n");
            for (BasicBlock basicBlock : basicBlocks) {
                sb.append(basicBlock.ir()).append("\n");
            }
            sb.append("}");
        }
        return sb.toString();
    }

}


