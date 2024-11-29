package frontend.llvm_ir;

import frontend.llvm_ir.*;
import frontend.llvm_ir.type.Type;
import frontend.node.EqExp;
import frontend.node.LAndExp;

import java.util.*;

/**
 * 代码还需要进行重构，考虑文法继承属性作为函数参数传递，综合属性作为函数返回值。
 * 同时符号表的设计还值得商榷
 * 下面考虑目前文法的一些重要综合属性和继承属性
 */

public class Visitor {

    public static final Model model = new Model();
    public static final irSymbolTable globalTable=new irSymbolTable();
    public static irSymbolTable curTable=globalTable;

    public static void pushScope(){
        irSymbolTable newTable=new irSymbolTable();
        newTable.setPre(curTable);
        curTable=newTable;
    }
    public static void popScope(){
        curTable=curTable.getPre();
    }

    /**
     * 继承属性：Value 的类型。在常变量的定义中，initial 表达式会用到该类型。
     */
    public static Type ValueType;

    /**
     * 继承属性：数组元素个数，用于数组相关的定义。
     */
    public static int ArraySize;
    /**
     * 综合属性：各种指令的结果值，特别是对表达式（exp）的值计算结果。
     */
    public static Value upValue;

    /**
     * 综合属性：常量表达式（constExp）的计算值。
     */
    public static int upConstValue;

    /**
     * 综合属性：局部数组的元素值集合。
     */
    public static ArrayList<Value> upArrayValue;

    /**
     * 继承属性：标识表达式是否可计算，常量表达式（constExp）始终可计算。
     */
    public static boolean calAble;
    /**
     * 继承属性：当前正在处理的函数。
     */
    public static Function curFunc;
    /**
     * 继承属性：当前正在操作的基本块。
     */
    public static BasicBlock curBlock;

    /**
     * 继承属性：函数的返回类型。
     */
    public static Type returnType;

    public static ArrayList<EqExp> eqExps;

    public static ArrayList<LAndExp> lAndExps;
    /**
     * 综合属性：正确分支的基本块栈。
     */
    public static BasicBlock trueBlock ;
    /**
     * 综合属性：错误分支的基本块栈。
     */
    public static BasicBlock falseBlock ;
    /**
     * 综合属性：`continue` 语句跳转的目标基本块栈。
     */
    public static final Stack<BasicBlock> continueToBlocks = new Stack<>();
    /**
     * 综合属性：`break` 语句跳转的目标基本块栈。
     */
    public static final Stack<BasicBlock> breakToBlocks = new Stack<>();
    /**
     * 综合属性：`printf` 声明的全局字符串池。
     * 每个字符串映射到一个全局变量。
     */
    public static final HashMap<String, GlobalVariable> stringPool = new HashMap<>();
    /**
     * 判断当前是否位于全局作用域。
     * @return 如果当前符号表是全局符号表，则返回 true。
     */
    public static boolean isGlobal() {
        return curTable == globalTable;
    }
}

