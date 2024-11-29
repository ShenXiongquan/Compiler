package frontend.node;

import frontend.llvm_ir.Function;
import frontend.llvm_ir.Parameter;
import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.type.IntegerType;
import frontend.llvm_ir.type.PointerType;
import frontend.llvm_ir.type.VoidType;
import frontend.node.decl.Decl;

import java.util.ArrayList;
import java.util.List;

public class CompUnit extends node {
    public final List<Decl> decls = new ArrayList<>();
    public final List<FuncDef> funcDefs = new ArrayList<>();
    public MainFuncDef MainFunctionDef;

    public String print() {
        StringBuilder sb = new StringBuilder();

        for (Decl decl : decls) {
            sb.append(decl.print());
        }
        for (FuncDef funcDef : funcDefs) {
            sb.append(funcDef.print());
        }
        sb.append(MainFunctionDef.print());
        sb.append("<CompUnit>\n");
        System.out.println("CompUnit Tree is built");

        return sb.toString();
    }


    public void visit() {
        //加入已有的声明函数
        Visitor.model.addGlobalValue(new Function("getint", IntegerType.i32, new ArrayList<>(), false));
        Visitor.model.addGlobalValue(new Function("getchar", IntegerType.i32, new ArrayList<>(), false));
        Visitor.model.addGlobalValue(new Function("putint", new VoidType(), new ArrayList<>() {{
            add(new Parameter("", IntegerType.i32));
        }}, false));
        Visitor.model.addGlobalValue(new Function("putch", new VoidType(), new ArrayList<>() {{
            add(new Parameter("", IntegerType.i32));
        }}, false));
        Visitor.model.addGlobalValue(new Function("putstr", new VoidType(), new ArrayList<>() {{
            add(new Parameter("", new PointerType(IntegerType.i8)));
        }}, false));

        decls.forEach(Decl::visit);
        funcDefs.forEach(FuncDef::visit);
        MainFunctionDef.visit();

    }


}//编译单元
