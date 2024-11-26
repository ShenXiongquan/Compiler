package frontend.llvm_ir;

import frontend.llvm_ir.type.FunctionType;
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
public class Function extends GlobalValue{

    public static int VarNum=0;public static int ifNum=1;public static int forNum=1;public static int breakNum=1;public static int continueNum=1;public static int returnNum=1;public static int andNum=1;public static int orNum=1;
    private final LinkedList<BasicBlock> basicBlocks = new LinkedList<>(); // 函数中的基本块列表
    private final boolean isDefine; // 是否是内建函数
    private final ArrayList<Parameter> parameters; // 函数的形参列表
    private boolean sideEffect = false; // 函数是否具有副作用
    private final HashSet<Function> callers = new HashSet<>(); // 调用该函数的其他函数

    /**
     *
     * @param name 函数名
     * @param returnType 返回类型
     * @param parameters 参数列表
     * @param isDefine 是否为自己定义的
     */
    public Function(String name, Type returnType, ArrayList<Parameter> parameters, boolean isDefine) {
        super(name, new FunctionType(returnType));
        this.parameters = parameters;
        this.isDefine = isDefine;
        ifNum=0;forNum=0;continueNum=0;breakNum=0;returnNum=0;andNum=0;orNum=0;
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

    @Override
    public FunctionType getType() {
        return (FunctionType) super.getType();
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public void addBasicBlock(BasicBlock block){
        basicBlocks.add(block);
    }

    public void removeBasicBlock(BasicBlock block){
        basicBlocks.remove(block);
    }

    //define dso_local i32 @main(){}
    //declare i32 @getint()
    @Override
    public String ir() {
        StringBuilder sb = new StringBuilder(isDefine ? "define dso_local ":"declare " );
        sb.append(getType().getReturnType().ir()).append(" ").append(getName()).append("(");
        //函数形参
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
        //函数体
        if(isDefine){
            sb.append("{\n");
            for (BasicBlock basicBlock : basicBlocks) {
                sb.append(basicBlock.ir()).append("\n");
            }
            sb.append("}");
        }
        return sb.toString();
    }

}


