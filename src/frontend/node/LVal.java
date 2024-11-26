package frontend.node;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.constants.ConstInt;
import frontend.llvm_ir.instructions.MemInstructions.getelementptr;
import frontend.llvm_ir.instructions.MemInstructions.load;
import frontend.llvm_ir.type.ArrayType;
import frontend.llvm_ir.type.IntegerType;
import frontend.llvm_ir.type.PointerType;
import frontend.llvm_ir.type.Type;
import frontend.symbol.Symbol;
import frontend.token.token;
import frontend.tool.myWriter;

public class LVal extends node {
    public token ident;
    public token lbrack;

    public Exp exp;
    public token rbrack;

    public void print() {
        ident.print();
        if (lbrack != null) {
            lbrack.print();
            exp.print();
            if (rbrack != null) rbrack.print();
        }
        myWriter.writeNonTerminal("LVal");
    }

    /**
     * LVal传常量或者地址，加不加载由上层来决定
     */
    public void visit() {
        //lval需要查符号表
        Value lVal = Visitor.curTable.getSymbolValue(ident.token());

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
                    getelementptr getelementptr = new getelementptr(lVal, ConstInt.zero, ConstInt.zero);//获取数组基地址
                    Visitor.curBlock.addInstruction(getelementptr);
                    Visitor.upValue = getelementptr;//传数组基地址
                } else {
                    exp.visit();//数组索引
                    Visitor.upValue = zext(Visitor.upValue);

                    getelementptr getelementptr = new getelementptr(lVal, ConstInt.zero, Visitor.upValue);//获取数组元素地址
                    Visitor.curBlock.addInstruction(getelementptr);
                    Visitor.upValue = getelementptr;//传数组元素地址
                }
            } else {
                //数组形参的使用会涉及二重指针,假设a是函数参数,f(int a[])
                if (exp == null) { //a;
                    load load = new load(lVal);//获取数组基地址
                    Visitor.curBlock.addInstruction(load);
                    Visitor.upValue = load;//传数组基地址
                } else { //a[exp];
                    load load = new load(lVal);//获取数组基地址
                    Visitor.curBlock.addInstruction(load);
                    exp.visit();//获取数组索引
                    Visitor.upValue = zext(Visitor.upValue);
                    getelementptr getelementptr = new getelementptr(load, Visitor.upValue);//获取数组元素地址
                    Visitor.curBlock.addInstruction(getelementptr);
                    Visitor.upValue = getelementptr;//传数组元素地址
                }
            }
        } else {
            throw new UnsupportedOperationException("Unsupported type: " + lVal.getType().ir());
        }


    }
}//左值表达式 LVal → Ident ['[' Exp ']']
