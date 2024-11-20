package frontend.node;

import frontend.Visitor;
import frontend.ir.Parameter;
import frontend.ir.BasicBlock;
import frontend.ir.Function;
import frontend.ir.instructions.ControlFlowInstructions.ret;
import frontend.ir.instructions.Instruction;
import frontend.ir.instructions.MemInstructions.alloca;
import frontend.ir.instructions.MemInstructions.store;
import frontend.symbol.Symbol;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;

public class FuncDef extends node{
    public FuncType funcType;
    public token ident;

    public token lparent;
    public FuncFParams funcFParams;

    public token rparent;

    public Block block;

    public void print(){
        funcType.print();
        ident.print();
        lparent.print();
        if(funcFParams!=null)funcFParams.print();
        if(rparent!=null)rparent.print();
        block.print();
        myWriter.writeNonTerminal("FuncDef");
    }
    @Override
    public void visit() {
        System.out.println("现在创建的函数是："+ident.token());
        funcType.visit();

        Visitor.curTable=Visitor.curTable.pushScope();
        Visitor.parameters =new ArrayList<>();

        Function.VarNum=0;
        if(funcFParams!=null) funcFParams.visit();

        Function function=new Function(ident.token(),Visitor.returnType,Visitor.parameters,true);
        Visitor.curFunc=function;
        Visitor.model.addGlobalValue(function);
        Visitor.curTable.pre.addSymbol(new Symbol(ident.token(), function));
        Visitor.curBlock= new BasicBlock(function);
        Visitor.curFunc.addBasicBlocks(Visitor.curBlock);

        for(Parameter parameter :Visitor.parameters){
            System.out.println("函数参数类型: "+parameter.getType().ir());
            alloca alloca = new alloca(parameter.getType());
            Visitor.curBlock.addInstruction(alloca);
            Visitor.curTable.addSymbol(new Symbol(parameter.getArgName(), alloca));

            store store = new store(parameter, alloca);
            Visitor.curBlock.addInstruction(store);
        }

        block.visit();
        Instruction last=Visitor.curBlock.getLastInstruction();
        if(!Visitor.curBlock.isJumpInstruction(last)){
           ret ret=new ret();
           Visitor.curBlock.addInstruction(ret);
        }
        Visitor.curTable=Visitor.curTable.popScope();

    }
}//函数声明FuncDef → FuncType Ident '(' [FuncFParams] ')' Block
