package frontend.ir;

import frontend.ir.instructions.ControlFlowInstructions.ControlFlowInstr;
import frontend.ir.instructions.ControlFlowInstructions.br;
import frontend.ir.instructions.Instruction;
import frontend.ir.type.FunctionType;
import frontend.ir.type.Type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * 函数，一个函数由若干基本块（BasicBlock）组成，树的第二层
 * declare i32 @getint()
 * declare i32 @getchar()
 * declare void @putint(i32)
 * declare void @putch(i32)
 * declare void @putstr(i8*)
 */
public class Function extends GlobalValue{

    public static int VarNum=0;
    private final LinkedList<BasicBlock> basicBlocks = new LinkedList<>(); // 函数中的基本块列表
    private final boolean isDefine; // 是否是内建函数
    private final ArrayList<Parameter> parameters; // 函数的形参列表

    private boolean sideEffect = false; // 函数是否具有副作用
    private final HashSet<Function> callers = new HashSet<>(); // 调用该函数的其他函数

    /**
     * @param name       函数名
     * @param returnType 函数的返回类型
     * @param parasType   函数的形参类型
     * @param isDefine   函数是否为自定义
     */
    public Function(String name, Type returnType, boolean isDefine,Type... parasType) {
        super(name, new FunctionType(returnType));
        this.isDefine = isDefine;
        this.parameters =new ArrayList<>();
        for (Type type : parasType) {
            Parameter parameter = new Parameter(null,type);
            parameters.add(parameter);
        }
    }

    public Function(String name, Type returnType, ArrayList<Parameter> parameters, boolean isDefine) {
        super(name, new FunctionType(returnType));
        this.parameters = parameters;
        this.isDefine = isDefine;
    }


    public void setSideEffect(boolean sideEffect) {
        this.sideEffect = sideEffect;
    }

    public boolean getSideEffect() {
        return sideEffect;
    }

    public HashSet<Function> getCallers() {
        return callers;
    }

    public BasicBlock getFirstBlock() {
        return  basicBlocks.get(0);
    }

    public void insertBlock(BasicBlock block) {
        basicBlocks.add(block);
    }

    public LinkedList<BasicBlock> getBasicBlocks() {
        return basicBlocks;
    }
    @Override
    public FunctionType getType() {
        return (FunctionType) super.getType();
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public  int getParaNum() {
        return parameters.size();
    }

    public void addBasicBlocks(BasicBlock block){
        basicBlocks.add(block);
    }

    //define dso_local i32 @main(){}
    //declare i32 @getint()
    @Override
    public String ir() {
        StringBuilder sb = new StringBuilder(isDefine ? "define dso_local ":"declare " );
        sb.append(getType().getReturnType().ir()).append(" ").append(getName()).append("(");
        if (!parameters.isEmpty()) {
            if(isDefine){
                sb.append(parameters.get(0).ir());
                for (int i = 1; i < parameters.size(); i++) {
                    sb.append(", ");
                    sb.append(parameters.get(i).ir());
                }
            }else {
                sb.append(parameters.get(0).getType().ir());
                for (int i = 1; i < parameters.size(); i++) {
                    sb.append(", ");
                    sb.append(parameters.get(i).getType().ir());
                }
            }
        }
        sb.append(")");

        if(isDefine){
            sb.append("{\n");
            for (BasicBlock basicBlock : basicBlocks) {
                if(basicBlock.getInstructions().isEmpty())continue;
                sb.append(basicBlock.ir()).append("\n");
            }
            sb.append("}");
        }
        return sb.toString();
    }


}

// 检查是否需要添加无条件跳转
//                if (basicBlock.getInstructions().isEmpty() || !(basicBlock.getLastInstruction() instanceof ControlFlowInstr)) {
//                        basicBlock.addInstruction(new br(basicBlocks.get(i+1)));
//                        }
