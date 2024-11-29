package frontend.node;

import frontend.llvm_ir.Parameter;
import frontend.token.token;

import java.util.ArrayList;
import java.util.List;

public class FuncFParams extends node {
    public final List<FuncFParam> funcFParamList = new ArrayList<>();
    public final List<token> comma = new ArrayList<>();

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(funcFParamList.get(0).print());
        for (int i = 1; i < funcFParamList.size(); i++) {
            sb.append(comma.get(i - 1).print());
            sb.append(funcFParamList.get(i).print());
        }
        sb.append("<FuncFParams>\n");
        return sb.toString();
    }


    public void visit(ArrayList<Parameter> parameters) {

        for (FuncFParam funcFParam : this.funcFParamList) {
            funcFParam.visit(parameters);
        }
    }

}//函数形参表
