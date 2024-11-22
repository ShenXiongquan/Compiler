package frontend.node;

import frontend.Visitor;
import frontend.ir.BasicBlock;
import frontend.ir.Function;
import frontend.ir.type.IntegerType;
import frontend.symbol.Symbol;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;

public class MainFuncDef extends node {
    public token intToken;
    public token main;

    public token lparent;

    public token rparent;
    public Block block;

    public void print() {
        intToken.print();
        main.print();
        lparent.print();
        if (rparent != null) rparent.print();
        block.print();
        myWriter.writeNonTerminal("MainFuncDef");
    }
    @Override
    public void visit() {

        Visitor.curTable=Visitor.curTable.pushScope();
        Function.VarNum=0;
        Function function=new Function(main.token(), IntegerType.i32,new ArrayList<>(),true);
        Visitor.curFunc=function;
        Visitor.model.addGlobalValue(function);
        Visitor.curTable.pre.addSymbol(new Symbol(main.token(), function));
        Visitor.curBlock= new BasicBlock();
        
        Visitor.curFunc.addBasicBlocks(Visitor.curBlock);
        block.visit();
        Visitor.curTable=Visitor.curTable.popScope();
    }
}//主函数声明MainFuncDef → 'int' 'main' '(' ')' Block
