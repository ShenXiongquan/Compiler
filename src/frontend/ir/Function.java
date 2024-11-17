package frontend.ir;

import frontend.ir.type.FunctionType;
import frontend.ir.type.Type;
import frontend.symbol.SymbolTable;

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
    private final LinkedList<BasicBlock> basicBlocks = new LinkedList<>(); // 函数中的基本块列表
    class Argument extends Value{

        public Argument(int num, Type valueType) {
            super(Value.LOCAL_PREFIX+num,valueType);
        }

        @Override
        public String ir() {
            return getType().ir()+" "+ getName();
        }
    }
    private final boolean isDefine; // 是否是内建函数

    private final List<Argument> arguments = new ArrayList<>(); // 函数的形参列表


    private final SymbolTable symbolTable=new SymbolTable();
    private boolean sideEffect = false; // 函数是否具有副作用
    private final HashSet<Function> callers = new HashSet<>(); // 调用该函数的其他函数

    /**
     *
     * @param name 函数名
     * @param returnType 函数的返回类型
     * @param isDefine 函数是否为自定义
     * @param args 函数的形参类型
     */
    public Function(String name, Type returnType, boolean isDefine, Type ...args) {
        super(name, new FunctionType(returnType,List.of(args)));

        this.isDefine = isDefine;

        for (int i = 0; i < getType().getParmNum(); i++){
            Argument argument = new Argument(i, getType().getParameterList().get(i));
            arguments.add(argument);
            addFunctionSymbol(argument);
        }
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

    public void addFunctionSymbol(Value value) {

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

    //define dso_local i32 @main(){}
    //declare i32 @getint()
    @Override
    public String ir() {
        StringBuilder sb = new StringBuilder(isDefine ? "define dso_local ":"declare " );
        sb.append(this.getType().getReturnType().ir()+" "+this.getName()+"(");
        if (!arguments.isEmpty()) {
            sb.append(arguments.get(0).ir());
            for (int i = 1; i < arguments.size(); i++) {
                sb.append(", ");
                sb.append(arguments.get(i).ir());
            }
        }
        sb.append(")");
        if(isDefine){
            sb.append("{\n");
            for(BasicBlock basicBlock:basicBlocks){
                sb.append(basicBlock.ir()).append("\n");
            }
            sb.append("}");
        }
        return sb.toString();
    }


}
