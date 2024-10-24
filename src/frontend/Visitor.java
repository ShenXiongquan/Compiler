package frontend;

import frontend.node.*;
import frontend.node.decl.*;
import frontend.node.blockItem.*;
import frontend.node.constInitVal.*;
import frontend.node.initVal.*;
import frontend.node.primaryExp.*;
import frontend.node.stmt.*;
import frontend.node.unaryExp.*;
import frontend.symbol.Symbol;
import frontend.symbol.SymbolTable;
import frontend.symbol.SymbolType;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.errorManager;
import frontend.tool.myWriter;

import java.util.*;

/**
 代码还需要进行重构，考虑文法继承属性作为函数参数传递，综合属性作为函数返回值。
 同时符号表的设计还值得商榷
 下面考虑目前文法的一些重要综合属性和继承属性

*/
public class Visitor {
    private int scopeLevel=1;//目前的作用域
    private SymbolTable curTable=new SymbolTable(scopeLevel,0);
    private final Stack<SymbolTable> symbolTableStack = new Stack<>();
    private final LinkedHashMap<Integer,SymbolTable> symbolTableMap=new LinkedHashMap<>();
    private void pushTable(){
        int FatherId=symbolTableStack.peek().id;
        curTable=new SymbolTable(++scopeLevel,FatherId);
        symbolTableMap.put(curTable.id,curTable);
        symbolTableStack.push(curTable);
    }

    private void popTable(){
        symbolTableStack.pop();
        curTable=symbolTableStack.peek();
    }

    private int isLoop=0;

    private boolean hasReturnValue=false;

    private boolean isConstant(LVal lVal){
        SymbolTable symbolTable=curTable;
        while (true){
            if(symbolTable.directory.containsKey(lVal.ident.token())){
                SymbolType symbolType=symbolTable.directory.get(lVal.ident.token()).symbolType;
                if(symbolType==SymbolType.CONST_INT||symbolType==SymbolType.CONST_CHAR)return true;
                else if ((symbolType==SymbolType.CONST_INT_ARRAY||symbolType==SymbolType.CONST_CHAR_ARRAY)&&lVal.lbrack!=null) return true;
                else return false;
            }
            if(symbolTable.fatherId==0)break;
            symbolTable=symbolTableMap.get(symbolTable.fatherId);
        }
        return false;
    }//判断是常量
    private boolean isIdentifierUndeclared(token ident){
        SymbolTable symbolTable=curTable;
        while (true){
            if(symbolTable.directory.containsKey(ident.token())) return false;
            if(symbolTable.fatherId==0)break;
            symbolTable=symbolTableMap.get(symbolTable.fatherId);
        }
        return true;
    }//未声明的符号

    private SymbolType getIdentifierType(token ident){
        SymbolTable symbolTable=curTable;
        while (true){
            if(symbolTable.directory.containsKey(ident.token())) return symbolTable.directory.get(ident.token()).symbolType;
            if(symbolTable.fatherId==0)break;
            symbolTable=symbolTableMap.get(symbolTable.fatherId);
        }
       return null;
    }//获取符号类型
    private SymbolType getParamType(Exp exp){
        UnaryExp currentUnaryExp = exp.addExp.mulExp.unaryExp;
        if (!(currentUnaryExp instanceof PrimaryExpUE primaryExpUE)) {
           return null;
        }

        if (!(primaryExpUE.primaryExp instanceof LValPE)) {
           return null;
        }
        LVal lVal= ((LValPE) primaryExpUE.primaryExp).lVal;
        if(getIdentifierType(lVal.ident)==SymbolType.INT_ARRAY&&lVal.lbrack==null){
            return SymbolType.INT_ARRAY;
        }else if (getIdentifierType(lVal.ident)==SymbolType.CHAR_ARRAY&&lVal.lbrack==null) {
            return SymbolType.CHAR_ARRAY;
        }else {
            return null;
        }
    }//获取实参类型
    public void visit(CompUnit compUnit){
        symbolTableStack.push(curTable);
        symbolTableMap.put(1,curTable);

        for(Decl decl: compUnit.decls){
            visitDecl(decl);
        }
        for(FuncDef funcDef: compUnit.funcDefs){
            visitFuncDef(funcDef);
        }
        visitMainFuncDef(compUnit.MainFunctionDef);
    }
    private void visitDecl(Decl decl) {
        if (decl instanceof CONSTDecl) {
            visitConstDecl(((CONSTDecl) decl).constDecl);
        } else if (decl instanceof VARDecl) {
            visitVarDecl((((VARDecl) decl).varDecl));
        }
    }
    private void visitConstDecl(ConstDecl constDecl){

       for(ConstDef constDef: constDecl.constDefs){
           visitConstDef(constDef, constDecl.bType);
       }
    }

    private void visitConstDef(ConstDef constDef,BType bType) {
        Symbol symbol=new Symbol();
        if(bType.type.type()==tokenType.CHARTK){
            if(constDef.lbrack==null) {symbol.symbolType=SymbolType.CONST_CHAR;}
            else {symbol.symbolType=SymbolType.CONST_CHAR_ARRAY;}
        }else{
            if(constDef.lbrack==null) {symbol.symbolType=SymbolType.CONST_INT;}
            else {symbol.symbolType=SymbolType.CONST_INT_ARRAY;}
        }

        symbol.tableId=curTable.id;
        symbol.token=constDef.ident.token();

        if(curTable.directory.containsKey(symbol.token)) errorManager.handleError(constDef.ident.line(),"b");
        else curTable.directory.put(symbol.token,symbol);//b类错误
        if(constDef.lbrack!=null)visitConstExp(constDef.constExp);
        visitConstInitVal(constDef.constInitVal);
    }

    private void visitConstInitVal(ConstInitVal constInitVal) {
        if(constInitVal instanceof ConstExpCIV){
            visitConstExp(((ConstExpCIV) constInitVal).constExp);
        } else if (constInitVal instanceof  ConstExpArrayCIV) {
            for(ConstExp constExp:((ConstExpArrayCIV) constInitVal).constExps){
                visitConstExp(constExp);
            }
        }
    }

    private void visitVarDecl(VarDecl varDecl){
        for(VarDef varDef: varDecl.varDefs){
            visitVarDef(varDef,varDecl.bType);
        }
    }

    private void visitVarDef(VarDef varDef, BType bType) {
        Symbol symbol=new Symbol();
        if(bType.type.type()==tokenType.CHARTK){

            if(varDef.lbrack==null) {symbol.symbolType=SymbolType.CHAR_VAR;}
            else {symbol.symbolType=SymbolType.CHAR_ARRAY;}
        }else{

            if(varDef.lbrack==null) {symbol.symbolType=SymbolType.INT_VAR;}
            else {symbol.symbolType=SymbolType.INT_ARRAY;}
        }

        symbol.tableId=curTable.id;
        symbol.token=varDef.ident.token();
        if(curTable.directory.containsKey(symbol.token)) errorManager.handleError(varDef.ident.line(),"b");
        else curTable.directory.put(symbol.token,symbol);
        if(varDef.lbrack!=null)visitConstExp(varDef.constExp);
        visitInitVal(varDef.initVal);
    }

    private void visitInitVal(InitVal initVal) {
        if(initVal instanceof ExpIV){
            visitExp(((ExpIV) initVal).exp);
        } else if (initVal instanceof ExpArrayIV) {
            for (Exp exp: ((ExpArrayIV) initVal).exps) {
                visitExp(exp);
            }
        }
    }

    private void visitFuncDef(FuncDef funcDef){
        Symbol symbol=new Symbol();
        if(funcDef.funcType.returnType.type()==tokenType.CHARTK){
            symbol.symbolType=SymbolType.CHAR_FUNC;

        }else if (funcDef.funcType.returnType.type()==tokenType.INTTK) {
            symbol.symbolType=SymbolType.INT_FUNC;
        }else {
            symbol.symbolType=SymbolType.VOID_FUNC;
        }


        symbol.token=funcDef.ident.token();
        symbol.tableId=curTable.id;

        if(curTable.directory.containsKey(symbol.token)) errorManager.handleError(funcDef.ident.line(),"b");
        else curTable.directory.put(symbol.token,symbol);

        pushTable();//进入新的作用域，创建新的符号表入栈
        if(funcDef.funcFParams!=null){
            visitFuncFParams(funcDef.funcFParams);
            symbol.paramNum=funcDef.funcFParams.funcFParams.size();
            for(Symbol param:curTable.directory.values()){
                symbol.paramTypeList.add(param.symbolType);
            }
        }

        hasReturnValue=(funcDef.funcType.returnType.type()!=tokenType.VOIDTK);
        visitBlock(funcDef.block);

        if(funcDef.funcType.returnType.type()!=tokenType.VOIDTK){
            if(funcDef.block.blockItems.isEmpty()){
                errorManager.handleError(funcDef.block.rbrace.line(),"g");
            }else{
                BlockItem blockItem=funcDef.block.blockItems.get(funcDef.block.blockItems.size()-1);
                if(!(blockItem instanceof StmtBlockItem)||!(((StmtBlockItem) blockItem).stmt instanceof ReturnStmt)){
                    errorManager.handleError(funcDef.block.rbrace.line(),"g");
                }
            }
        }

    }//FuncDef → FuncType Ident '(' [FuncFParams] ')' Block//难处理，可能存在bug

    private void visitMainFuncDef(MainFuncDef mainFuncDef){
        hasReturnValue=true;
        pushTable();//进入新的作用域，创建新的符号表入栈
        visitBlock(mainFuncDef.block);
        if(mainFuncDef.block.blockItems.isEmpty()){
            errorManager.handleError(mainFuncDef.block.rbrace.line(),"g");
        }else {
            BlockItem blockItem=mainFuncDef.block.blockItems.get(mainFuncDef.block.blockItems.size()-1);
            if(!(blockItem instanceof StmtBlockItem)||!(((StmtBlockItem) blockItem).stmt instanceof ReturnStmt)){
                errorManager.handleError(mainFuncDef.block.rbrace.line(),"g");
            }
        }
    }
    private void visitFuncFParams(FuncFParams funcFParams) {

        for(FuncFParam funcFParam:funcFParams.funcFParams){
            visitFuncFParam(funcFParam);
        }
    }

    private void visitFuncFParam(FuncFParam funcFParam) {
        Symbol symbol=new Symbol();
        if(funcFParam.bType.type.type()==tokenType.INTTK){
            if(funcFParam.lbrack!=null){symbol.symbolType=SymbolType.INT_ARRAY;}
            else {symbol.symbolType=SymbolType.INT_VAR;}
        }else {
            if(funcFParam.lbrack!=null){symbol.symbolType=SymbolType.CHAR_ARRAY;}
            else {symbol.symbolType=SymbolType.CHAR_VAR;}
        }

        symbol.token=funcFParam.ident.token();
        symbol.tableId=curTable.id;

        if(curTable.directory.containsKey(symbol.token)) errorManager.handleError(funcFParam.ident.line(),"b");
        else curTable.directory.put(symbol.token,symbol);

    }//FuncFParam → BType Ident ['[' ']']

    private void visitBlock(Block block) {
        for(BlockItem blockItem:block.blockItems){
            visitBlockItem(blockItem);
        }
        popTable();//退出当前作用域，符号表弹出栈
    }

    private void visitBlockItem(BlockItem blockItem) {
        if(blockItem instanceof DeclBlockItem){
            visitDecl(((DeclBlockItem) blockItem).decl);
        }else {
            visitStmt(((StmtBlockItem)blockItem).stmt);
        }
    }

    private void visitStmt(Stmt stmt) {

        if(stmt instanceof AssignStmt){
           LVal lVal=((AssignStmt) stmt).lVal;
           if(isConstant(lVal))errorManager.handleError(lVal.ident.line(),"h");
           visitLVal(lVal);
           visitExp(((AssignStmt) stmt).exp);
        } else if (stmt instanceof ExpressionStmt) {
            if(((ExpressionStmt) stmt).optionalExp!=null){
                visitExp(((ExpressionStmt) stmt).optionalExp);
            }
        }else if (stmt instanceof BlockStmt) {
            pushTable();//进入新的作用域，创建新的符号表入栈
            visitBlock(((BlockStmt) stmt).block);
        } else if (stmt instanceof IfStmt) {
            visitCond(((IfStmt) stmt).cond);
            visitStmt(((IfStmt) stmt).thenStmt);
            if(((IfStmt) stmt).elseStmt!=null)visitStmt(((IfStmt) stmt).elseStmt);

        } else if (stmt instanceof LoopStmt) {
            if(((LoopStmt) stmt).initForStmt!=null)visitForStmt(((LoopStmt) stmt).initForStmt);
            if(((LoopStmt) stmt).forCondition!=null)visitCond(((LoopStmt) stmt).forCondition);
            if(((LoopStmt) stmt).updateForStmt!=null)visitForStmt(((LoopStmt) stmt).updateForStmt);
            isLoop++;
            visitStmt(((LoopStmt) stmt).forBody);
            isLoop--;

        } else if (stmt instanceof BreakStmt) {
            if(isLoop==0){
                errorManager.handleError(((BreakStmt) stmt).breakToken.line(),"m");
            }
        } else if (stmt instanceof ContinueStmt) {
            if(isLoop==0){
                errorManager.handleError(((ContinueStmt) stmt).continueToken.line(),"m");
            }
        } else if (stmt instanceof ReturnStmt) {
            if(((ReturnStmt) stmt).returnExp!=null){
                if(!hasReturnValue)errorManager.handleError(((ReturnStmt) stmt).returnToken.line(),"f");
                visitExp(((ReturnStmt) stmt).returnExp);
            }
        } else if (stmt instanceof GetIntStmt) {
            LVal lVal=((GetIntStmt) stmt).lVal;
            if(isConstant(lVal))errorManager.handleError(lVal.ident.line(),"h");
            visitLVal(lVal);
        } else if (stmt instanceof GetCharStmt) {
            LVal lVal=((GetCharStmt) stmt).lVal;
            if(isConstant(lVal))errorManager.handleError(lVal.ident.line(),"h");
            visitLVal(lVal);
        } else {
            int count =0;
            String s=((PrintfStmt)stmt).stringConst.token();
            int len=s.length();
            for(int i=0;i<len;i++){
                if(s.charAt(i)=='%'&&(s.charAt(i+1)=='d'||s.charAt(i+1)=='c'))count++;
            }
            if(count!=((PrintfStmt) stmt).exps.size()){
                errorManager.handleError(((PrintfStmt) stmt).printf.line(),"l");
            }
            for(Exp exp:((PrintfStmt) stmt).exps){
                visitExp(exp);
            }
        }
    }//难处理，可能存在bug
    private void visitForStmt(ForStmt forStmt) {
        if(isConstant(forStmt.lVal))errorManager.handleError(forStmt.lVal.ident.line(),"h");
        visitLVal(forStmt.lVal);
        visitExp(forStmt.exp);
    }
    private void visitExp(Exp exp) {
        visitAddExp(exp.addExp);
    }
    private void visitCond(Cond cond) {
        visitLOrExp(cond.lOrExp);
    }
    private void visitLVal(LVal lVal){
        if(isIdentifierUndeclared(lVal.ident)) {
            errorManager.handleError(lVal.ident.line(),"c");
        }
        if(lVal.exp!=null)visitExp(lVal.exp);
    }
    private void visitPrimaryExp(PrimaryExp primaryExp) {
        if(primaryExp instanceof ExpPE){
            visitExp(((ExpPE) primaryExp).exp);
        } else if (primaryExp instanceof LValPE) {
            visitLVal(((LValPE) primaryExp).lVal);
        }
    }
    private void visitUnaryExp(UnaryExp unaryExp){
        if(unaryExp instanceof PrimaryExpUE ){
            visitPrimaryExp(((PrimaryExpUE) unaryExp).primaryExp);
        }else if (unaryExp instanceof FuncCallUE funcCallUE){
            token ident=funcCallUE.ident;
            if(isIdentifierUndeclared(ident)){
                errorManager.handleError(ident.line(),"c");
            }
            else if(symbolTableMap.get(1).directory.get(ident.token()).paramNum!= (funcCallUE.funcRParams!=null ? funcCallUE.funcRParams.exps.size() : 0)){
               errorManager.handleError(ident.line(),"d");
            }else{
                Symbol func=symbolTableMap.get(1).directory.get(ident.token());
                for(int i=0;i<func.paramNum;i++){
                    SymbolType paramtype=getParamType(((FuncCallUE) unaryExp).funcRParams.exps.get(i));
                    if(paramtype==null&&func.paramTypeList.get(i).getTypeName().endsWith("Array")){
                        errorManager.handleError(ident.line(), "e");//数组参数传变量
                        break;
                    } else if (paramtype!=null&&func.paramTypeList.get(i)!=paramtype) {
                        errorManager.handleError(ident.line(), "e");//数组类型不匹配或者变量参数传数组
                        break;
                    }
                }
            }
            if(funcCallUE.funcRParams!=null) visitFuncRParams(funcCallUE.funcRParams);
        }else{
            visitUnaryExp(((UnaryOpUE)unaryExp).unaryExp);
        }
    }//难处理，可能存在bug
    private void visitFuncRParams(FuncRParams funcRParams) {
        for(Exp exp:funcRParams.exps){
            visitExp(exp);
        }
    }
    private void visitMulExp(MulExp mulExp) {
        if(mulExp.mulExp!=null)visitMulExp(mulExp.mulExp);
        visitUnaryExp(mulExp.unaryExp);
    }
    private void visitAddExp(AddExp addExp) {
        if(addExp.addExp!=null) visitAddExp(addExp.addExp);
        visitMulExp(addExp.mulExp);
    }
    private void visitRelExp(RelExp relExp) {
        if(relExp.relExp!=null)visitRelExp(relExp.relExp);
        visitAddExp(relExp.addExp);
    }
    private void visitEqExp(EqExp eqExp) {
        if(eqExp.eqExp!=null)visitEqExp(eqExp.eqExp);
        visitRelExp(eqExp.relExp);
    }
    private void visitLAndExp(LAndExp lAndExp) {
        if (lAndExp.lAndExp!=null)visitLAndExp(lAndExp.lAndExp);
        visitEqExp(lAndExp.eqExp);
    }
    private void visitLOrExp(LOrExp lOrExp) {
        if(lOrExp.lOrExp!=null)visitLOrExp(lOrExp.lOrExp);
        visitLAndExp(lOrExp.lAndExp);
    }
    private void visitConstExp(ConstExp constExp){
        visitAddExp(constExp.addExp);
    }
    public void write(){
        for(SymbolTable symbolTable:symbolTableMap.values()){
            for (Map.Entry<String, Symbol> entry : symbolTable.directory.entrySet()) {
                Symbol symbol=entry.getValue();
                System.out.println(symbol.tableId+" "+symbol.token+" "+symbol.symbolType.getTypeName());
                myWriter.writeSymbol(entry.getValue());
            }
        }
    }
}
