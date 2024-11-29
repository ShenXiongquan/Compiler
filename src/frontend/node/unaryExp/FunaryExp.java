package frontend.node.unaryExp;

import frontend.llvm_ir.Function;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.node.FuncRParams;
import frontend.token.token;

import java.util.ArrayList;

public class FunaryExp extends UnaryExp {
    public token ident;
    public token lparent;
    public FuncRParams funcRParams;
    public token rparent;

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.print());
        sb.append(lparent.print());
        if (funcRParams != null) {
            sb.append(funcRParams.print());
        }
        if (rparent != null) {
            sb.append(rparent.print());
        }
        sb.append("<UnaryExp>\n");
        return sb.toString();
    }


    public void visit() {
        System.out.println("现在调用的函数是：" + ident.name());

        Function function = (Function) Visitor.curTable.getSymbolValue(ident.name());

        ArrayList<Value> args = new ArrayList<>();
        if (funcRParams != null) funcRParams.visit(function.getParameters(), args);

        System.out.println("函数实参个数:" + args.size());

        Visitor.upValue = call(function, args.toArray(Value[]::new));
    }
}
