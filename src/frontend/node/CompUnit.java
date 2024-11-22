package frontend.node;

import frontend.Visitor;
import frontend.ir.Function;
import frontend.ir.GlobalValue;
import frontend.ir.GlobalVariable;
import frontend.ir.Model;
import frontend.ir.type.*;
import frontend.node.decl.Decl;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class CompUnit extends node{
    public final List<Decl> decls = new ArrayList<>();
    public final List<FuncDef> funcDefs = new ArrayList<>();
    public MainFuncDef MainFunctionDef;

    public void print() {
        for (Decl decl : decls) decl.print();
        for (FuncDef funcDef : funcDefs) funcDef.print();
        MainFunctionDef.print();
        myWriter.writeNonTerminal("CompUnit");
        System.out.println("CompUnit Tree is built");
    }

    public void visit(){
        System.out.println("全局符号表:"+Visitor.curTable.id);
        //加入已有的声明函数
        Visitor.model.addGlobalValue(new Function("getint",IntegerType.i32,false));
        Visitor.model.addGlobalValue(new Function("getchar",IntegerType.i32,false));
        Visitor.model.addGlobalValue(new Function("putint",new VoidType(),false,IntegerType.i32));
        Visitor.model.addGlobalValue(new Function("putch",new VoidType(),false,IntegerType.i32));
        Visitor.model.addGlobalValue(new Function("putstr",new VoidType(),false,new PointerType(IntegerType.i8)));

        decls.forEach(Decl::visit);
        funcDefs.forEach(FuncDef::visit);
        MainFunctionDef.visit();

    }


}//编译单元
