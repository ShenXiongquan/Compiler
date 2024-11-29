package frontend.node;

import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.type.IntegerType;
import frontend.token.token;

import java.util.ArrayList;

public class MainFuncDef extends node {
    public token intToken;
    public token main;
    public token lparent;
    public token rparent;
    public Block block;

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(intToken.print());
        sb.append(main.print());
        sb.append(lparent.print());
        if (rparent != null) sb.append(rparent.print());
        sb.append(block.print());
        sb.append("<MainFuncDef>\n");
        return sb.toString();
    }


    public void visit() {

        Visitor.pushScope();
        Function.VarNum = 0;
        Function function = new Function(main.name(), IntegerType.i32, new ArrayList<>(), true);
        Visitor.curFunc = function;
        Visitor.model.addGlobalValue(function);
        Visitor.globalTable.addSymbol(main.name(), function);

        enterNewBlock(new BasicBlock("entry"));

        block.visit();
        Visitor.popScope();

        if (Visitor.curBlock.isEmpty()) {
            Visitor.curFunc.removeBasicBlock(Visitor.curBlock);
        }
    }
}//主函数声明MainFuncDef → 'int' 'main' '(' ')' Block
