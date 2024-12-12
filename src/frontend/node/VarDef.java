package frontend.node;


import frontend.llvm_ir.GlobalVar;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.constants.Constant;
import frontend.llvm_ir.constants.Zeroinitializer;
import frontend.llvm_ir.instructions.MemInstructions.alloca;
import frontend.llvm_ir.type.ArrayType;
import frontend.llvm_ir.type.IntegerType;
import frontend.node.initVal.InitVal;
import frontend.token.token;

public class VarDef extends node {
    public token ident;
    public token lbrack;
    public ConstExp constExp;
    public token rbrack;
    public token assign;
    public InitVal initVal;

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.print());
        if (lbrack != null) {
            sb.append(lbrack.print());
            sb.append(constExp.print());
            if (rbrack != null) {
                sb.append(rbrack.print());
            }
        }
        if (assign != null) {
            sb.append(assign.print());
            sb.append(initVal.print());
        }
        sb.append("<VarDef>\n");
        return sb.toString();
    }


    public void visit() {
        String variableName = ident.name(); // 变量名
        boolean isGlobal = Visitor.isGlobal(); // 是否为全局变量
        boolean isArray = (constExp != null); // 是否为数组
        boolean initialized = (initVal != null);
        if (!isArray) { // 普通变量
            if (isGlobal) { // 全局普通变量
                if (initialized) {
                    Visitor.calAble = true;
                    initVal.visit(null);
                    Visitor.calAble = false;
                }
                GlobalVar globalVar = new GlobalVar(variableName, initialized ? (Constant) Visitor.upValue : new ConstInt((IntegerType) Visitor.ValueType, 0), false);
                Visitor.model.addGlobalValue(globalVar);
                Visitor.curTable.addSymbol(variableName, globalVar);
            } else { // 局部普通变量
                alloca alloca = alloca(Visitor.ValueType); // 分配空间
                Visitor.curTable.addSymbol(variableName, alloca);
                if (initialized) {
                    initVal.visit(null);
                    store(Visitor.upValue, alloca); // 存储初始值

                }
            }
        } else { // 数组变量
            constExp.visit(); // 处理数组维度
            Visitor.ArraySize = Visitor.upConstValue;
            ArrayType arrayType = new ArrayType(Visitor.ValueType, Visitor.ArraySize); // 构建数组类型

            if (isGlobal) { // 全局数组
                if (initialized) {
                    Visitor.calAble = true;
                    initVal.visit(null);
                    Visitor.calAble = false;
                }
                GlobalVar globalVar = new GlobalVar(variableName, initialized ? (Constant) Visitor.upValue : new Zeroinitializer(arrayType), false);
                Visitor.model.addGlobalValue(globalVar);
                Visitor.curTable.addSymbol(variableName, globalVar);
            } else { // 局部数组
                alloca alloca = alloca(arrayType); // 分配空间
                Visitor.curTable.addSymbol(variableName, alloca);
                if (initialized) {
                    initVal.visit(alloca);
//                    int i = 0;
//                    for (Value element : Visitor.upArrayValue) {
//                        getelementptr getelementptr = getelementptr(alloca, ConstInt.zero, new ConstInt(IntegerType.i32, i++));
//                        store(element, getelementptr);
//                    }
                }
            }
        }
    }//变量定义 VarDef → Ident [ '[' ConstExp ']' ] | Ident [ '[' ConstExp ']' ] '='InitVal
}