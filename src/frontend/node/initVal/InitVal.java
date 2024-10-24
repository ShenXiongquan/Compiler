package frontend.node.initVal;


public abstract class InitVal {

    public abstract void visit();
//    public void visit(){
//        if(stringConst!=null){
//            stringConst.visit();
//        }else if(lbrace!=null){
//            lbrace.visit();
//            if(!exps.isEmpty()){
//                exps.get(0).visit();
//                for(int i=1;i<exps.size();i++){
//                    comma.visit();
//                    exps.get(i).visit();
//                }
//            }
//            rbrace.visit();
//        }else {
//            exps.get(0).visit();
//        }
//        myWriter.writeNonTerminal("InitVal");
//    }
}//变量初值
