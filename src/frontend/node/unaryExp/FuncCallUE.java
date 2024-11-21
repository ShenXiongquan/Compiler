package frontend.node.unaryExp;

import frontend.Visitor;
import frontend.ir.Value;
import frontend.ir.instructions.MixedInstructions.call;
import frontend.node.FuncRParams;
import frontend.token.token;
import frontend.tool.myWriter;
import frontend.ir.Function;

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

    @Override
    public void visit() {
        System.out.println("现在调用的函数是："+ident.token());

        Function function= (Function) Visitor.curTable.getSymbol(ident.token()).value;

        Visitor.parameters=function.getParameters();
        if(funcRParams!=null)funcRParams.visit();

        call call=new call(function,Visitor.args.toArray(Value[]::new));
        Visitor.curBlock.addInstruction(call);
        Visitor.upValue=call;
    }
}
