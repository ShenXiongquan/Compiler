package frontend.node;

import frontend.Visitor;
import frontend.ir.type.IntegerType;
import frontend.ir.type.PointerType;
import frontend.ir.type.Type;
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
    @Override
    public void visit() {

        int i=0;
        Visitor.args=new ArrayList<>();
        for(Exp exp:exps){
            Type paraType=Visitor.parameters.get(i++).getType();
            Visitor.lValNotLoad=paraType instanceof PointerType;
            exp.visit();
            Visitor.lValNotLoad=false;

            if(paraType instanceof IntegerType expectedType){
                if(expectedType.isInt32())Visitor.upValue=zext(Visitor.upValue);
                else Visitor.upValue=trunc(Visitor.upValue);
            }

            Visitor.args.add(Visitor.upValue);
        }

    }
}//函数实参表
