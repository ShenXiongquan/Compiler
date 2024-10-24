package frontend.node;

import frontend.node.decl.Decl;
import frontend.tool.myWriter;
import java.util.ArrayList;
import java.util.List;

public class CompUnit {
    public final List<Decl> decls=new ArrayList<>();
    public final List<FuncDef> funcDefs=new ArrayList<>();
    public MainFuncDef MainFunctionDef;

    public void visit(){
      if(!decls.isEmpty()) for(Decl decl:decls) decl.visit();
      if(!funcDefs.isEmpty())for (FuncDef funcDef:funcDefs)funcDef.visit();
      MainFunctionDef.visit();
      myWriter.writeNonTerminal("CompUnit");
      myWriter.close();
      System.out.println("CompUnit is written");
    }
}//编译单元
