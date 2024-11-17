package frontend.node;


import frontend.node.initVal.InitVal;
import frontend.token.token;
import frontend.tool.myWriter;

public class VarDef extends node {
    public token ident;
    public token lbrack;
    public ConstExp constExp;
    public token rbrack;
    public token assign;
    public InitVal initVal;

    public void print() {
        ident.print();
        if (lbrack != null) {
            lbrack.print();
            constExp.print();
            if (rbrack != null) rbrack.print();
        }
        if (assign != null) {
            assign.print();
            initVal.print();
        }
        myWriter.writeNonTerminal("VarDef");
    }
    @Override
    public void visit() {

    }
}//变量定义 VarDef → Ident [ '[' ConstExp ']' ] | Ident [ '[' ConstExp ']' ] '='InitVal
