package frontend.node;

import frontend.llvm_ir.Parameter;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class FuncFParams extends node{
    public final List<FuncFParam> funcFParamList =new ArrayList<>();
    public final List<token> comma=new ArrayList<>();

    public void print(){
        funcFParamList.get(0).print();
        for(int i = 1; i< funcFParamList.size(); i++){
            comma.get(i-1).print();
            funcFParamList.get(i).print();
        }
        myWriter.writeNonTerminal("FuncFParams");
    }


    public void visit(ArrayList<Parameter> parameters) {

        for(FuncFParam funcFParam: this.funcFParamList){
            funcFParam.visit(parameters);
        }
    }

}//函数形参表
