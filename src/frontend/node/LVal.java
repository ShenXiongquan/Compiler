package frontend.node;

import frontend.Visitor;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.type.IntegerType;
import frontend.ir.type.Type;
import frontend.token.token;
import frontend.tool.myWriter;

public class LVal extends node{
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
    @Override
    public void visit() {
        Value lVal= Visitor.curTable.getSymbol(ident.token()).value;
        if(lVal.getType() instanceof IntegerType){
            if(Visitor.calAble){
                Visitor.upConstValue=((ConstInt) lVal).getValue();
            }
            Visitor.upValue=lVal;
        }else{ //  lVal 指向的是一个局部变量或者全局变量

        }

    }
}//左值表达式 LVal → Ident ['[' Exp ']']
