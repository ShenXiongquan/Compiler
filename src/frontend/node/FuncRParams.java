package frontend.node;

import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.Parameter;
import frontend.llvm_ir.Value;
import frontend.llvm_ir.type.IntegerType;
import frontend.llvm_ir.type.Type;
import frontend.token.token;
import frontend.tool.myWriter;
import java.util.ArrayList;
import java.util.List;


public class FuncRParams extends node{
    public final List<Exp> exps=new ArrayList<>();
    public List<token> comma=new ArrayList<>();

    public void print(){
        exps.get(0).print();
        for(int i=1;i<exps.size();i++){
            comma.get(i-1).print();
            exps.get(i).print();
        }
        myWriter.writeNonTerminal("FuncRParams");
    }

    public void visit(ArrayList<Parameter> parameters, ArrayList<Value> args) {

        int i=0;

        for(Exp exp:exps){
            Type paraType=parameters.get(i++).getType();
            System.out.println("参数类型:"+paraType.ir());
            exp.visit();

            if(paraType instanceof IntegerType expectedType){
                if(expectedType.isInt32())Visitor.upValue=zext(Visitor.upValue);
                else Visitor.upValue=trunc(Visitor.upValue);
            }//如果参数不是指针类型的，需要考虑扩展

            args.add(Visitor.upValue);
        }
    }

}//函数实参表
