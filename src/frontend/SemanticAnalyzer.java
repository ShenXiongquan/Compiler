package frontend;

import frontend.node.*;
import frontend.node.blockItem.BlockItem;
import frontend.node.blockItem.DeclBlockItem;
import frontend.node.blockItem.StmtBlockItem;
import frontend.node.constInitVal.ArrayConstInitVal;
import frontend.node.constInitVal.ConstInitVal;
import frontend.node.constInitVal.ExpConstInitVal;
import frontend.node.decl.ConstDecl;
import frontend.node.decl.Decl;
import frontend.node.decl.VarDecl;
import frontend.node.initVal.ArrayInitVal;
import frontend.node.initVal.ExpInitVal;
import frontend.node.initVal.InitVal;
import frontend.node.primaryExp.ExpPE;
import frontend.node.primaryExp.LValPE;
import frontend.node.primaryExp.PrimaryExp;
import frontend.node.stmt.*;
import frontend.node.unaryExp.FuncCallUE;
import frontend.node.unaryExp.PrimaryExpUE;
import frontend.node.unaryExp.UnaryExp;
import frontend.node.unaryExp.UnaryOpUE;
import frontend.symbol.Symbol;
import frontend.symbol.SymbolTable;
import frontend.symbol.SymbolType;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.errorManager;
import frontend.tool.myWriter;

import java.util.Map;

public class SemanticAnalyzer {
    private  final SymbolTable globalTable = new SymbolTable();//先创建全局符号表，符号表是树形的，全局符号表是根节点
    private SymbolTable curTable = globalTable;
    private  int isLoop = 0;
    private  boolean hasReturnValue = false;

    private boolean isConstant(LVal lVal) {

        SymbolTable symbolTable = curTable;
        do {
            if (symbolTable.directory.containsKey(lVal.ident.token())) {
                SymbolType symbolType = symbolTable.directory.get(lVal.ident.token()).symbolType;
                if (symbolType == SymbolType.CONST_INT || symbolType == SymbolType.CONST_CHAR) return true;
                else if ((symbolType == SymbolType.CONST_INT_ARRAY || symbolType == SymbolType.CONST_CHAR_ARRAY) && lVal.lbrack != null)
                    return true;
                else return false;
            }
            symbolTable = symbolTable.pre;
        } while (symbolTable != null);

        return false;
    }//判断是常量

    private boolean isIdentifierUndeclared(token ident) {
        return curTable.getSymbol(ident.token())==null;
    }//未声明的符号

    private SymbolType getIdentifierType(token ident) {
        SymbolTable symbolTable = curTable;
        do {
            if (symbolTable.directory.containsKey(ident.token()))
                return symbolTable.directory.get(ident.token()).symbolType;
            symbolTable = symbolTable.pre;
        } while (symbolTable != null);
        return null;
    }//获取符号类型

    private SymbolType getParamType(Exp exp) {
        UnaryExp currentUnaryExp = exp.addExp.mulExp.unaryExp;
        if (!(currentUnaryExp instanceof PrimaryExpUE primaryExpUE)) {
            return null;
        }

        if (!(primaryExpUE.primaryExp instanceof LValPE)) {
            return null;
        }
        LVal lVal = ((LValPE) primaryExpUE.primaryExp).lVal;
        if (getIdentifierType(lVal.ident) == SymbolType.INT_ARRAY && lVal.lbrack == null) {
            return SymbolType.INT_ARRAY;
        } else if (getIdentifierType(lVal.ident) == SymbolType.CHAR_ARRAY && lVal.lbrack == null) {
            return SymbolType.CHAR_ARRAY;
        } else {
            return null;
        }
    }//获取实参类型

    public void visit(CompUnit compUnit) {

        for (Decl decl : compUnit.decls) {
            visitDecl(decl);
        }
        for (FuncDef funcDef : compUnit.funcDefs) {
            visitFuncDef(funcDef);
        }
        visitMainFuncDef(compUnit.MainFunctionDef);
    }

    private void visitDecl(Decl decl) {
        if (decl instanceof ConstDecl) {
            visitConstDecl(((ConstDecl) decl).constDecl);
        } else if (decl instanceof VarDecl) {
            visitVarDecl((((VarDecl) decl).varDecl));
        }
    }

    private void visitConstDecl(frontend.node.ConstDecl constDecl) {

        for (ConstDef constDef : constDecl.constDefs) {
            visitConstDef(constDef, constDecl.bType);
        }
    }

    private void visitConstDef(ConstDef constDef, BType bType) {
        Symbol symbol = new Symbol();
        if (bType.type.type() == tokenType.CHARTK) {
            if (constDef.lbrack == null) {
                symbol.symbolType = SymbolType.CONST_CHAR;
            } else {
                symbol.symbolType = SymbolType.CONST_CHAR_ARRAY;
            }
        } else {
            if (constDef.lbrack == null) {
                symbol.symbolType = SymbolType.CONST_INT;
            } else {
                symbol.symbolType = SymbolType.CONST_INT_ARRAY;
            }
        }

        symbol.tableId = curTable.id;
        symbol.token = constDef.ident.token();

        if(!curTable.addSymbol(symbol)) errorManager.handleError(constDef.ident.line(), "b");

        if (constDef.lbrack != null) visitConstExp(constDef.constExp);
        visitConstInitVal(constDef.constInitVal);
    }

    private void visitConstInitVal(ConstInitVal constInitVal) {
        if (constInitVal instanceof ExpConstInitVal expConstInitVal) {
            visitConstExp(expConstInitVal.constExp);
        } else if (constInitVal instanceof ArrayConstInitVal constExpArrayCIV) {
            for (ConstExp constExp : constExpArrayCIV.constExps) {
                visitConstExp(constExp);
            }
        }
    }

    private void visitVarDecl(frontend.node.VarDecl varDecl) {
        for (VarDef varDef : varDecl.varDefs) {
            visitVarDef(varDef, varDecl.bType);
        }
    }

    private void visitVarDef(VarDef varDef, BType bType) {
        Symbol symbol = new Symbol();
        if (bType.type.type() == tokenType.CHARTK) {

            if (varDef.lbrack == null) {
                symbol.symbolType = SymbolType.CHAR_VAR;
            } else {
                symbol.symbolType = SymbolType.CHAR_ARRAY;
            }
        } else {

            if (varDef.lbrack == null) {
                symbol.symbolType = SymbolType.INT_VAR;
            } else {
                symbol.symbolType = SymbolType.INT_ARRAY;
            }
        }

        symbol.tableId = curTable.id;
        symbol.token = varDef.ident.token();
        if(!curTable.addSymbol(symbol)) errorManager.handleError(varDef.ident.line(), "b");

        if (varDef.lbrack != null) visitConstExp(varDef.constExp);
        visitInitVal(varDef.initVal);
    }

    private void visitInitVal(InitVal initVal) {
        if (initVal instanceof ExpInitVal expInitVal) {
            visitExp(expInitVal.exp);
        } else if (initVal instanceof ArrayInitVal arrayInitVal) {
            for (Exp exp : arrayInitVal.exps) {
                visitExp(exp);
            }
        }
    }

    private void visitFuncDef(FuncDef funcDef) {
        Symbol symbol = new Symbol();
        if (funcDef.funcType.returnType.type() == tokenType.CHARTK) {
            symbol.symbolType = SymbolType.CHAR_FUNC;

        } else if (funcDef.funcType.returnType.type() == tokenType.INTTK) {
            symbol.symbolType = SymbolType.INT_FUNC;
        } else {
            symbol.symbolType = SymbolType.VOID_FUNC;
        }

        symbol.token = funcDef.ident.token();
        symbol.tableId = curTable.id;
        if(!curTable.addSymbol(symbol))errorManager.handleError(funcDef.ident.line(), "b");


        curTable=curTable.pushScope();//进入新的作用域，创建新的符号表入栈
        if (funcDef.funcFParams != null) {
            visitFuncFParams(funcDef.funcFParams);
            symbol.paramNum = funcDef.funcFParams.arguments.size();
            symbol.paramList.addAll(curTable.directory.values());
        }

        hasReturnValue = (funcDef.funcType.returnType.type() != tokenType.VOIDTK);
        visitBlock(funcDef.block);

        if (funcDef.funcType.returnType.type() != tokenType.VOIDTK) {
            if (funcDef.block.blockItems.isEmpty()) {
                errorManager.handleError(funcDef.block.rbrace.line(), "g");
            } else {
                BlockItem blockItem = funcDef.block.blockItems.get(funcDef.block.blockItems.size() - 1);
                if (!(blockItem instanceof StmtBlockItem) || !(((StmtBlockItem) blockItem).stmt instanceof ReturnStmt)) {
                    errorManager.handleError(funcDef.block.rbrace.line(), "g");
                }
            }
        }

    }//FuncDef → FuncType Ident '(' [FuncFParams] ')' Block//难处理，可能存在bug

    private void visitMainFuncDef(MainFuncDef mainFuncDef) {
        hasReturnValue = true;
        curTable=curTable.pushScope();//进入新的作用域
        visitBlock(mainFuncDef.block);
        if (mainFuncDef.block.blockItems.isEmpty()) {
            errorManager.handleError(mainFuncDef.block.rbrace.line(), "g");
        } else {
            BlockItem blockItem = mainFuncDef.block.blockItems.get(mainFuncDef.block.blockItems.size() - 1);
            if (!(blockItem instanceof StmtBlockItem) || !(((StmtBlockItem) blockItem).stmt instanceof ReturnStmt)) {
                errorManager.handleError(mainFuncDef.block.rbrace.line(), "g");
            }
        }
    }

    private void visitFuncFParams(FuncFParams funcFParams) {

        for (FuncFParam funcFParam : funcFParams.arguments) {
            visitFuncFParam(funcFParam);
        }
    }

    private void visitFuncFParam(FuncFParam funcFParam) {
        Symbol symbol = new Symbol();
        if (funcFParam.bType.type.type() == tokenType.INTTK) {
            if (funcFParam.lbrack != null) {
                symbol.symbolType = SymbolType.INT_ARRAY;
            } else {
                symbol.symbolType = SymbolType.INT_VAR;
            }
        } else {
            if (funcFParam.lbrack != null) {
                symbol.symbolType = SymbolType.CHAR_ARRAY;
            } else {
                symbol.symbolType = SymbolType.CHAR_VAR;
            }
        }

        symbol.token = funcFParam.ident.token();
        symbol.tableId = curTable.id;

        if(!curTable.addSymbol(symbol))errorManager.handleError(funcFParam.ident.line(), "b");
        else curTable.directory.put(symbol.token, symbol);

    }//FuncFParam → BType Ident ['[' ']']

    private void visitBlock(Block block) {
        for (BlockItem blockItem : block.blockItems) {
            visitBlockItem(blockItem);
        }
        curTable=curTable.popScope();//退出当前作用域
    }

    private void visitBlockItem(BlockItem blockItem) {
        if (blockItem instanceof DeclBlockItem declBlockItem) {
            visitDecl(declBlockItem.decl);
        } else {
            visitStmt(((StmtBlockItem) blockItem).stmt);
        }
    }

    private void visitStmt(Stmt stmt) {

        if (stmt instanceof AssignStmt assignStmt) {
            LVal lVal = assignStmt.lVal;
            if (isConstant(lVal)) {
                errorManager.handleError(lVal.ident.line(), "h");
            }
            visitLVal(lVal);
            visitExp(assignStmt.exp);

        } else if (stmt instanceof ExpressionStmt expressionStmt) {
            if (expressionStmt.optionalExp != null) {
                visitExp(expressionStmt.optionalExp);
            }

        } else if (stmt instanceof BlockStmt blockStmt) {
            curTable=curTable.pushScope(); // 进入新的作用域
            visitBlock(blockStmt.block);

        } else if (stmt instanceof IfStmt ifStmt) {
            visitCond(ifStmt.cond);
            visitStmt(ifStmt.trueStmt);
            if (ifStmt.falseStmt != null) {
                visitStmt(ifStmt.falseStmt);
            }

        } else if (stmt instanceof LoopStmt loopStmt) {
            if (loopStmt.initForStmt != null) {
                visitForStmt(loopStmt.initForStmt);
            }
            if (loopStmt.forCondition != null) {
                visitCond(loopStmt.forCondition);
            }
            if (loopStmt.updateForStmt != null) {
                visitForStmt(loopStmt.updateForStmt);
            }
            isLoop++;
            visitStmt(loopStmt.forBody);
            isLoop--;

        } else if (stmt instanceof BreakStmt breakStmt) {
            if (isLoop == 0) {
                errorManager.handleError(breakStmt.breakToken.line(), "m");
            }

        } else if (stmt instanceof ContinueStmt continueStmt) {
            if (isLoop == 0) {
                errorManager.handleError(continueStmt.continueToken.line(), "m");
            }

        } else if (stmt instanceof ReturnStmt returnStmt) {
            if (returnStmt.returnExp != null) {
                if (!hasReturnValue) {
                    errorManager.handleError(returnStmt.returnToken.line(), "f");
                }
                visitExp(returnStmt.returnExp);
            }

        } else if (stmt instanceof GetIntStmt getIntStmt) {
            LVal lVal = getIntStmt.lVal;
            if (isConstant(lVal)) {
                errorManager.handleError(lVal.ident.line(), "h");
            }
            visitLVal(lVal);

        } else if (stmt instanceof GetCharStmt getCharStmt) {
            LVal lVal = getCharStmt.lVal;
            if (isConstant(lVal)) {
                errorManager.handleError(lVal.ident.line(), "h");
            }
            visitLVal(lVal);

        } else if (stmt instanceof PrintfStmt printfStmt) {
            int count = 0;
            String s = printfStmt.stringConst.token();
            int len = s.length();
            for (int i = 0; i < len; i++) {
                if (s.charAt(i) == '%' && (s.charAt(i + 1) == 'd' || s.charAt(i + 1) == 'c')) {
                    count++;
                }
            }
            if (count != printfStmt.exps.size()) {
                errorManager.handleError(printfStmt.printf.line(), "l");
            }
            for (Exp exp : printfStmt.exps) {
                visitExp(exp);
            }
        }
    }//难处理，可能存在bug

    private void visitForStmt(ForStmt forStmt) {
        if (isConstant(forStmt.lVal)) errorManager.handleError(forStmt.lVal.ident.line(), "h");
        visitLVal(forStmt.lVal);
        visitExp(forStmt.exp);
    }

    private void visitExp(Exp exp) {
        visitAddExp(exp.addExp);
    }

    private void visitCond(Cond cond) {
        visitLOrExp(cond.lOrExp);
    }

    private void visitLVal(LVal lVal) {

        if (isIdentifierUndeclared(lVal.ident)) {
            errorManager.handleError(lVal.ident.line(), "c");
        }
        if (lVal.exp != null) visitExp(lVal.exp);
    }

    private void visitPrimaryExp(PrimaryExp primaryExp) {
        if (primaryExp instanceof ExpPE expPE) {
            visitExp(expPE.exp);
        } else if (primaryExp instanceof LValPE lValPE) {
            visitLVal(lValPE.lVal);
        }
    }

    private void visitUnaryExp(UnaryExp unaryExp) {
        if (unaryExp instanceof PrimaryExpUE primaryExpUE) {
            visitPrimaryExp(primaryExpUE.primaryExp);
        } else if (unaryExp instanceof FuncCallUE funcCallUE) {
            token ident = funcCallUE.ident;
            if (isIdentifierUndeclared(ident)) {
                errorManager.handleError(ident.line(), "c");
            } else if (globalTable.directory.get(ident.token()).paramNum != (funcCallUE.funcRParams != null ? funcCallUE.funcRParams.exps.size() : 0)) {
                errorManager.handleError(ident.line(), "d");
            } else {
                Symbol func = globalTable.directory.get(ident.token());
                for (int i = 0; i < func.paramNum; i++) {
                    SymbolType paramtype = getParamType(((FuncCallUE) unaryExp).funcRParams.exps.get(i));
                    if (paramtype == null && func.paramList.get(i).symbolType.getTypeName().endsWith("Array")) {
                        errorManager.handleError(ident.line(), "e");//数组参数传变量
                        break;
                    } else if (paramtype != null && func.paramList.get(i).symbolType != paramtype) {
                        errorManager.handleError(ident.line(), "e");//数组类型不匹配或者变量参数传数组
                        break;
                    }
                }
            }
            if (funcCallUE.funcRParams != null) visitFuncRParams(funcCallUE.funcRParams);
        } else {
            visitUnaryExp(((UnaryOpUE) unaryExp).unaryExp);
        }
    }//难处理，可能存在bug

    private void visitFuncRParams(FuncRParams funcRParams) {
        for (Exp exp : funcRParams.exps) {
            visitExp(exp);
        }
    }

    private void visitMulExp(MulExp mulExp) {
        if (mulExp.mulExp != null) visitMulExp(mulExp.mulExp);
        visitUnaryExp(mulExp.unaryExp);
    }

    private void visitAddExp(AddExp addExp) {
        if (addExp.addExp != null) visitAddExp(addExp.addExp);
        visitMulExp(addExp.mulExp);
    }

    private void visitRelExp(RelExp relExp) {
        if (relExp.relExp != null) visitRelExp(relExp.relExp);
        visitAddExp(relExp.addExp);
    }

    private void visitEqExp(EqExp eqExp) {
        if (eqExp.eqExp != null) visitEqExp(eqExp.eqExp);
        visitRelExp(eqExp.relExp);
    }

    private void visitLAndExp(LAndExp lAndExp) {
        if (lAndExp.lAndExp != null) visitLAndExp(lAndExp.lAndExp);
        visitEqExp(lAndExp.eqExp);
    }

    private void visitLOrExp(LOrExp lOrExp) {
        if (lOrExp.lOrExp != null) visitLOrExp(lOrExp.lOrExp);
        visitLAndExp(lOrExp.lAndExp);
    }

    private void visitConstExp(ConstExp constExp) {
        visitAddExp(constExp.addExp);
    }

    public void write(SymbolTable symbolTable) {
        if (symbolTable != null) {
            for (Map.Entry<String, Symbol> entry : symbolTable.directory.entrySet()) {
                Symbol symbol = entry.getValue();
                System.out.println(symbol.tableId + " " + symbol.token + " " + symbol.symbolType.getTypeName());
                myWriter.writeSymbol(entry.getValue());
            }
            for (SymbolTable next : symbolTable.next) {
                write(next);
            }
        }
    }
}
