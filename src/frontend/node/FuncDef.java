package frontend.node;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.Parameter;
import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.instructions.Instruction;
import frontend.llvm_ir.instructions.MemInstructions.alloca;
import frontend.llvm_ir.type.VoidType;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;

public class FuncDef extends node {
    public FuncType funcType;
    public token ident;

    public token lparent;
    public FuncFParams funcFParams;

    public token rparent;

    public Block block;

    public void print() {
        funcType.print();
        ident.print();
        lparent.print();
        if (funcFParams != null) funcFParams.print();
        if (rparent != null) rparent.print();
        block.print();
        myWriter.writeNonTerminal("FuncDef");
    }

    public void visit() {
        System.out.println("现在创建的函数是：" + ident.token());
        funcType.visit();

        Visitor.pushScope();

        ArrayList<Parameter> parameters = new ArrayList<>();
        Function.VarNum = 0;

        if (funcFParams != null) {
            funcFParams.visit(parameters);
        }

        Function function = new Function(ident.token(), Visitor.returnType, parameters, true);
        Visitor.curFunc = function;
        Visitor.model.addGlobalValue(function);
        Visitor.globalTable.addSymbol(ident.token(), function);

        enterNewBlock(new BasicBlock("entry"));

        for (Parameter parameter : parameters) {
            System.out.println("函数参数类型: " + parameter.getType().ir());
            alloca alloca = alloca(parameter.getType());
            Visitor.curTable.addSymbol(parameter.getArgName(), alloca);
            store(parameter, alloca);
        }

        block.visit();
        Visitor.popScope();

        Instruction last = Visitor.curBlock.getLastInstruction();
        if (function.getType().getReturnType() instanceof VoidType && !Visitor.curBlock.isJumpInstruction(last)) {
            ret();
        } else if (Visitor.curBlock.isEmpty()) {
            Visitor.curFunc.removeBasicBlock(Visitor.curBlock);
        }
    }
}//函数声明FuncDef → FuncType Ident '(' [FuncFParams] ')' Block
