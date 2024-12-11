package frontend.node;

import frontend.llvm_ir.Parameter;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.type.IntegerType;
import frontend.llvm_ir.type.Type;
import frontend.token.token;

import java.util.ArrayList;
import java.util.List;


public class FuncRParams extends node {
    public final List<Exp> exps = new ArrayList<>();
    public final List<token> comma = new ArrayList<>();

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(exps.get(0).print());
        for (int i = 1; i < exps.size(); i++) {
            sb.append(comma.get(i - 1).print());
            sb.append(exps.get(i).print());
        }
        sb.append("<FuncRParams>\n");
        return sb.toString();
    }


    public void visit(ArrayList<Parameter> parameters, ArrayList<Value> args) {
        int i = 0;
        for (Exp exp : exps) {
            exp.visit();
            Type paraType = parameters.get(i).getType();
            if (paraType instanceof IntegerType expectedType) {
                if (expectedType.isInt32()) Visitor.upValue = zext(Visitor.upValue);
                else Visitor.upValue = trunc(Visitor.upValue);
            }//如果参数不是指针类型的，需要考虑扩展
            store2Stack(Visitor.upValue, i, exps.size());//实参在mips需要存到a0-a4或者栈里面
            args.add(Visitor.upValue);
            i++;
        }
    }

}//函数实参表
