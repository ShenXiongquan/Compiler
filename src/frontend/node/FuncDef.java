package frontend.node;

import frontend.llvm_ir.BasicBlock;
import frontend.llvm_ir.Function;
import frontend.llvm_ir.Parameter;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.instructions.Instruction;
import frontend.llvm_ir.instructions.MemInstructions.alloca;
import frontend.llvm_ir.type.VoidType;
import frontend.token.token;

import java.util.ArrayList;

public class FuncDef extends node {
    public FuncType funcType;
    public token ident;

    public token lparent;
    public FuncFParams funcFParams;

    public token rparent;

    public Block block;

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(funcType.print());
        sb.append(ident.print());
        sb.append(lparent.print());
        if (funcFParams != null) sb.append(funcFParams.print());
        if (rparent != null) sb.append(rparent.print());
        sb.append(block.print());
        sb.append("<FuncDef>\n");
        return sb.toString();
    }


    public void visit() {
        System.out.println("现在创建的函数是：" + ident.name());
        funcType.visit();

        Visitor.pushScope();

        ArrayList<Parameter> parameters = new ArrayList<>();
        Function.VarNum = 0;

        if (funcFParams != null) {
            funcFParams.visit(parameters);
        }

        Function function = new Function(ident.name(), Visitor.returnType, parameters, true);
        Visitor.curFunc = function;
        Visitor.model.addGlobalValue(function);
        Visitor.globalTable.addSymbol(ident.name(), function);

        enterNewBlock(new BasicBlock());

        for (Parameter parameter : parameters) {
            System.out.println("函数参数类型: " + parameter.getType().ir());
            alloca alloca = alloca(parameter.getType());
            Visitor.curTable.addSymbol(parameter.getArgName(), alloca);
            store(parameter, alloca);
        }

        block.visit();
        Visitor.popScope();

        Instruction last = Visitor.curBlock.getLastInstruction();
        if (function.getType() instanceof VoidType && !Visitor.curBlock.isJumpInstruction(last)) {
            ret();
        } else if (Visitor.curBlock.isEmpty()) {
            Visitor.curFunc.removeBasicBlock(Visitor.curBlock);
        }
    }
}//函数声明FuncDef → FuncType Ident '(' [FuncFParams] ')' Block
