package frontend.node;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.type.IntegerType;
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
    public void visit() {

        Visitor.pushScope();
        Function.VarNum=0;
        Function function=new Function(main.token(), IntegerType.i32,new ArrayList<>(),true);
        Visitor.curFunc=function;
        Visitor.model.addGlobalValue(function);
        Visitor.globalTable.addSymbol(main.token(), function);

        enterNewBlock(new BasicBlock("entry"));

        block.visit();
        Visitor.popScope();

        if(Visitor.curBlock.isEmpty()){
            Visitor.curFunc.removeBasicBlock(Visitor.curBlock);
        }
    }
}//主函数声明MainFuncDef → 'int' 'main' '(' ')' Block
