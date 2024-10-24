package frontend.node.unaryExp;


public abstract class UnaryExp {

    public abstract void visit();
//    public void visit(){
//        if(primaryExp!=null){
//            primaryExp.visit();
//        } else if (ident!=null) {
//            ident.visit();
//            lparent.visit();
//            if(funcRParams!=null)funcRParams.visit();
//            if(rparent!=null)rparent.visit();
//        }else {
//            unaryOp.visit();
//            unaryExp.visit();
//        }
//        myWriter.writeNonTerminal("UnaryExp");
//    }
}//一元表达式 UnaryExp → PrimaryExp | Ident '(' [FuncRParams] ')' | UnaryOp UnaryExp
