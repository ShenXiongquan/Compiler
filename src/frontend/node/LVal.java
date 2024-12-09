package frontend.node;

import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.constants.ConstArray;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.MemInstructions.load;
import frontend.llvm_ir.type.ArrayType;
import frontend.llvm_ir.type.IntegerType;
import frontend.llvm_ir.type.PointerType;
import frontend.llvm_ir.type.Type;
import frontend.token.token;

public class LVal extends node {
    public token ident;
    public token lbrack;

    public Exp exp;
    public token rbrack;

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.print());
        if (lbrack != null) {
            sb.append(lbrack.print());
            sb.append(exp.print());
            if (rbrack != null) {
                sb.append(rbrack.print());
            }
        }
        sb.append("<LVal>\n");
        return sb.toString();
    }


    /**
     * LVal传常量或者地址，加不加载由上层来决定
     */
    public void visit() {
        //lval需要查符号表
        Value lVal = Visitor.curTable.getSymbolValue(ident.name());

        if (lVal.getType() instanceof IntegerType) {//传常量
            Visitor.upConstValue = ((ConstInt) lVal).getValue();
            Visitor.upValue = new ConstInt(IntegerType.i32, ((ConstInt) lVal).getValue());
        } else if (lVal.getType() instanceof PointerType pointerType) {//传地址
            Type pointedType = pointerType.getPointedType();
            if (pointedType instanceof IntegerType) {
                Visitor.upValue = lVal;
            } else if (pointedType instanceof ArrayType) {
                //对于数组
                if (exp == null) {
                    Visitor.upValue = getelementptr(lVal, ConstInt.zero, ConstInt.zero);//传数组基地址
                } else {
                    exp.visit();//数组索引
                    Visitor.upValue = zext(Visitor.upValue);
                    if (lVal instanceof ConstArray constArray && Visitor.upValue instanceof ConstInt constInt) {
                        Visitor.upValue = constArray.getArrayElement(constInt.getValue());
                    } else {
                        Visitor.upValue = getelementptr(lVal, ConstInt.zero, Visitor.upValue);//传数组元素地址
                    }
                }
            } else {
                //数组形参的使用会涉及二重指针,假设a是函数参数,f(int a[])
                if (exp == null) { //a;
                    Visitor.upValue = load(lVal);//传数组基地址
                } else { //a[exp];
                    load load = load(lVal);//获取数组基地址
                    exp.visit();//获取数组索引
                    Visitor.upValue = zext(Visitor.upValue);
                    Visitor.upValue = getelementptr(load, Visitor.upValue);//传数组元素地址
                }
            }
        } else {
            throw new UnsupportedOperationException("Unsupported type: " + lVal.getType().ir());
        }

    }
}//左值表达式 LVal → Ident ['[' Exp ']']
