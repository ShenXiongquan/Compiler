package frontend.node;

import frontend.Visitor;
import frontend.ir.GlobalVariable;
import frontend.ir.constants.ConstArray;
import frontend.ir.constants.ConstInt;
import frontend.ir.constants.ConstStr;
import frontend.ir.constants.Constant;
import frontend.ir.instructions.MemInstructions.alloca;
import frontend.ir.instructions.MemInstructions.getelementptr;
import frontend.ir.instructions.MemInstructions.store;
import frontend.ir.type.ArrayType;
import frontend.ir.type.IntegerType;
import frontend.node.constInitVal.ConstInitVal;
import frontend.symbol.Symbol;
import frontend.token.token;
import frontend.tool.myWriter;

public class ConstDef extends node{
    public token ident;
    public token lbrack;
    public ConstExp constExp;
    public token rbrack;

    public token assign;
    public ConstInitVal constInitVal;

    public void print(){
        ident.print();
        if(lbrack!=null){
            lbrack.print();
            constExp.print();
            if(rbrack!=null)rbrack.print();
        }
        assign.print();
        constInitVal.print();
        myWriter.writeNonTerminal("ConstDef");
    }
    @Override
    public void visit() {
        //常量是一定可以计算初始值的，在编译时就需要确定这个值。但注意区分全局常量和局部常量

        if(constExp==null){//常量
            constInitVal.visit();
            if(Visitor.isGlobal()){  //全局常量
                GlobalVariable globalVariable=new GlobalVariable(ident.token(),Visitor.ValueType, (Constant) Visitor.upValue,true);
                Visitor.model.addGlobalValue(globalVariable);
            }
//            else{ //局部常量,用到alloca先分配空间，再用store存入
//                alloca alloca=new alloca(Visitor.ValueType,Visitor.curBlock);
//                Visitor.curBlock.addInstruction(alloca);
//                store store=new store(Visitor.upValue,alloca,Visitor.curBlock);
//                Visitor.curBlock.addInstruction(store);
//            }
            Visitor.curTable.addSymbol(new Symbol(Visitor.curTable.id,Visitor.upValue,true));
        }else {//常量数组
            constExp.visit();
            Visitor.ValueType=new ArrayType(Visitor.ValueType,Visitor.upConstValue);//构建数组类型
            constInitVal.visit();
            if(Visitor.isGlobal()){//全局常量数组
                //@a = dso_local global [10 x i32] [i32 1, i32 2, i32 3, i32 0, i32 0, i32 0, i32 0, i32 0, i32 0, i32 0]
                GlobalVariable globalVariable=new GlobalVariable(ident.token(),Visitor.ValueType, (Constant) Visitor.upValue,true);
                Visitor.model.addGlobalValue(globalVariable);
                Visitor.curTable.addSymbol(new Symbol(Visitor.curTable.id,Visitor.upValue,true));
            }else{//局部数组
                alloca alloca=new alloca(Visitor.ValueType,Visitor.curBlock);
                Visitor.curBlock.addInstruction(alloca);

                if(Visitor.upValue instanceof ConstArray){
                    int i=0;
                    for(ConstInt element:((ConstArray)Visitor.upValue).getArrayElements()){
                        getelementptr getelementptr=new getelementptr(alloca,ConstInt.zero,new ConstInt(IntegerType.i32,i++),Visitor.curBlock);
                        Visitor.curBlock.addInstruction(getelementptr);
                        store store=new store(element,getelementptr,Visitor.curBlock);
                        Visitor.curBlock.addInstruction(store);
                    }
                }else if(Visitor.upValue instanceof ConstStr){
                    store store=new store(Visitor.upValue,alloca,Visitor.curBlock);
                    Visitor.curBlock.addInstruction(store);
                }

                Visitor.curTable.addSymbol(new Symbol(Visitor.curTable.id,Visitor.upValue,true));
            }
        }


    }
}//常量定义ConstDef → Ident [ '[' ConstExp ']' ] '=' ConstInitVal
