package frontend.node.unaryExp;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.instructions.MixedInstructions.call;
import frontend.node.FuncRParams;
import frontend.token.token;
import frontend.tool.myWriter;
import frontend.llvm_ir.Function;

import java.util.ArrayList;

public class FuncCallUE extends UnaryExp{
    public token lparent;
    public token ident;
    public token rparent;
    public FuncRParams funcRParams;


    @Override
    public void print() {
        ident.print();
        lparent.print();
        if(funcRParams!=null)funcRParams.print();
        if(rparent!=null)rparent.print();
        myWriter.writeNonTerminal("UnaryExp");
    }

    public void visit() {
        System.out.println("现在调用的函数是："+ident.token());

        Function function= (Function) Visitor.curTable.getSymbolValue(ident.token());

        ArrayList<Value> args=new ArrayList<>();
        if(funcRParams!=null)funcRParams.visit(function.getParameters(),args);

        System.out.println("函数实参个数:"+args.size());
        call call=new call(function,args.toArray(Value[]::new));
        Visitor.curBlock.addInstruction(call);
        Visitor.upValue=call;
    }
}
