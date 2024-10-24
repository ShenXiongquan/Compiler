package frontend.node.primaryExp;


public abstract class PrimaryExp {

    public abstract void visit();

//    public void visit(){
//        if(lparent!=null){
//            lparent.visit();
//            exp.visit();
//            if(rparent!=null)rparent.visit();
//        } else if (lVal!=null) {
//            lVal.visit();
//        }else if (number!=null){
//            number.visit();
//        }else {
//            character.visit();
//        }
//
//        myWriter.writeNonTerminal("PrimaryExp");
//    }
}//基本表达式 PrimaryExp → '(' Exp ')' | LVal | Number | Character
