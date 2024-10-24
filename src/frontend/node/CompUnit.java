package frontend.node;

import frontend.node.decl.Decl;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class CompUnit {
    public final List<Decl> decls = new ArrayList<>();
    public final List<FuncDef> funcDefs = new ArrayList<>();
    public MainFuncDef MainFunctionDef;

    public void visit() {
        for (Decl decl : decls) decl.visit();
        for (FuncDef funcDef : funcDefs) funcDef.visit();
        MainFunctionDef.visit();
        myWriter.writeNonTerminal("CompUnit");
        System.out.println("CompUnit is written");
    }
}//编译单元
