package frontend.node;

import frontend.Visitor;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.instructions.MemInstructions.getelementptr;
import frontend.ir.instructions.MemInstructions.load;
import frontend.ir.type.ArrayType;
import frontend.ir.type.IntegerType;
import frontend.ir.type.PointerType;
import frontend.ir.type.Type;
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
     * LVal 必须是当前作用域内、该 Exp 语句之前有定义的变量或常量；对于赋值号左边的 LVal 必须是变量,难处理，可能存在bug
     */
    @Override
    public void visit() {
        //lval需要查符号表
        Symbol symbol = Visitor.curTable.getSymbol(ident.token());
        Value lVal = symbol.value;

        if (lVal.getType() instanceof IntegerType) {//常量
            if (Visitor.calAble) {
                Visitor.upConstValue = ((ConstInt) lVal).getValue();
            }
            Visitor.upValue = new ConstInt(IntegerType.i32, ((ConstInt) lVal).getValue());

        } else if (lVal.getType() instanceof PointerType pointerType) {//指针变量,比如alloca分配得到的地址
            Type pointedType = pointerType.getPointedType();
            if (pointedType instanceof IntegerType) {
                Visitor.upValue = lVal;
            } else if (pointedType instanceof ArrayType) {
                //对于非形参的数组
                if (exp == null) {
                    getelementptr getelementptr = new getelementptr(lVal, ConstInt.zero, ConstInt.zero);//获取数组基地址
                    Visitor.curBlock.addInstruction(getelementptr);
                    Visitor.upValue = getelementptr;
                } else {
                    exp.visit();
                    Visitor.upValue = zext(Visitor.upValue);
                    getelementptr getelementptr = new getelementptr(lVal, ConstInt.zero, Visitor.upValue);//获取数组元素地址
                    Visitor.curBlock.addInstruction(getelementptr);
                    Visitor.upValue = getelementptr;
                }
            } else {
                //数组形参的使用会涉及二重指针
                if (exp == null) {
                    load load = new load(lVal);//获取数组基地址
                    Visitor.curBlock.addInstruction(load);
                    Visitor.upValue = load;
                } else {
                    load load = new load(lVal);
                    Visitor.curBlock.addInstruction(load);
                    exp.visit();
                    Visitor.upValue = zext(Visitor.upValue);
                    getelementptr getelementptr = new getelementptr(load, Visitor.upValue);//获取数组元素地址
                    Visitor.curBlock.addInstruction(getelementptr);
                    Visitor.upValue = getelementptr;
                }

            }
        } else {
            throw new UnsupportedOperationException("Unsupported type: " + lVal.getType().ir());
        }


    }
}//左值表达式 LVal → Ident ['[' Exp ']']
