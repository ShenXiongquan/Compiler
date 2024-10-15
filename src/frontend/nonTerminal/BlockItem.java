package frontend.nonTerminal;

import frontend.nonTerminal.stmt.Stmt;

public class BlockItem {
    public Decl decl;

    public Stmt stmt;

    public void visit(){
        if(decl!=null)decl.visit();
        else stmt.visit();
    }
}//语句块项
