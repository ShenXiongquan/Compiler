package frontend;

import frontend.node.*;
import frontend.node.blockItem.BlockItem;
import frontend.node.blockItem.DBlockItem;
import frontend.node.blockItem.SBlockItem;
import frontend.node.constInitVal.ArrayConstInitVal;
import frontend.node.constInitVal.ConstInitVal;
import frontend.node.constInitVal.ExpConstInitVal;
import frontend.node.decl.CDecl;
import frontend.node.decl.Decl;
import frontend.node.decl.VDecl;
import frontend.node.initVal.ArrayInitVal;
import frontend.node.initVal.ExpInitVal;
import frontend.node.initVal.InitVal;
import frontend.node.primaryExp.CprimaryExp;
import frontend.node.primaryExp.EprimaryExp;
import frontend.node.primaryExp.LprimaryExp;
import frontend.node.primaryExp.PrimaryExp;
import frontend.node.stmt.*;
import frontend.node.unaryExp.FunaryExp;
import frontend.node.unaryExp.OunaryExp;
import frontend.node.unaryExp.PunaryExp;
import frontend.node.unaryExp.UnaryExp;
import frontend.symbol.Symbol;
import frontend.symbol.SymbolTable;
import frontend.symbol.SymbolType;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.errorManager;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.Map;

public class SemanticAnalyzer {
    private final CompUnit compUnit;
    private final SymbolTable globalTable = new SymbolTable();//先创建全局符号表，符号表是树形的，全局符号表是根节点
    private SymbolTable curTable = globalTable;
    private int isLoop = 0;
    private boolean hasReturnValue = false;

    public SemanticAnalyzer(CompUnit compUnit) {
        this.compUnit = compUnit;
    }

    private boolean isConstant(Symbol symbol) {
        if (symbol == null) return false;
        return symbol.isConst();

    }//判断是常量

    public void visit() {
        for (Decl decl : compUnit.decls) {
            visitDecl(decl);
        }
        for (FuncDef funcDef : compUnit.funcDefs) {
            visitFuncDef(funcDef);
        }
        visitMainFuncDef(compUnit.MainFunctionDef);
    }

    private void visitDecl(Decl decl) {
        if (decl instanceof CDecl cDecl) {
            visitConstDecl(cDecl.constDecl);
        } else if (decl instanceof VDecl vDecl) {
            visitVarDecl(vDecl.varDecl);
        }
    }

    private void visitConstDecl(frontend.node.ConstDecl constDecl) {

        for (ConstDef constDef : constDecl.constDefs) {
            visitConstDef(constDef, constDecl.bType);
        }
    }

    private void visitConstDef(ConstDef constDef, BType bType) {

        SymbolType symbolType = (bType.type.type() == tokenType.CHARTK) ? SymbolType.Char : SymbolType.Int;
        boolean isArray = (constDef.lbrack != null);

        Symbol symbol = new Symbol(
                curTable.id,
                constDef.ident.name(),
                true,
                isArray,
                symbolType
        );

        if (!curTable.addSymbol(symbol)) errorManager.handleError(constDef.ident.line(), "b");

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
        SymbolType symbolType = (bType.type.type() == tokenType.CHARTK) ? SymbolType.Char : SymbolType.Int;
        boolean isArray = (varDef.lbrack != null);

        Symbol symbol = new Symbol(
                curTable.id,
                varDef.ident.name(),
                false, // isConst
                isArray,
                symbolType
        );

        if (!curTable.addSymbol(symbol)) errorManager.handleError(varDef.ident.line(), "b");

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
        SymbolType symbolType = switch (funcDef.funcType.returnType.type()) {
            case CHARTK -> SymbolType.Char;
            case INTTK -> SymbolType.Int;
            default -> SymbolType.Void;
        };
        ArrayList<Symbol> paramList = new ArrayList<>();
        Symbol symbol = new Symbol(
                globalTable.id,
                funcDef.ident.name(),
                symbolType,
                paramList
        );

        // 尝试添加符号到当前作用域表
        if (!curTable.addSymbol(symbol)) errorManager.handleError(funcDef.ident.line(), "b");
        // 进入新作用域
        curTable = curTable.pushScope();
        // 如果函数有参数
        if (funcDef.funcFParams != null) {
            visitFuncFParams(funcDef.funcFParams);
            paramList.addAll(curTable.directory.values());
        }

        hasReturnValue = (funcDef.funcType.returnType.type() != tokenType.VOIDTK);
        visitBlock(funcDef.block);

        if (funcDef.funcType.returnType.type() != tokenType.VOIDTK) {
            if (funcDef.block.blockItems.isEmpty()) {
                errorManager.handleError(funcDef.block.rbrace.line(), "g");
            } else {
                BlockItem blockItem = funcDef.block.blockItems.get(funcDef.block.blockItems.size() - 1);
                if (!(blockItem instanceof SBlockItem sBlockItem) || !(sBlockItem.stmt instanceof ReturnStmt)) {
                    errorManager.handleError(funcDef.block.rbrace.line(), "g");
                }
            }
        }

    }//FuncDef → FuncType Ident '(' [FuncFParams] ')' Block//难处理，可能存在bug

    private void visitMainFuncDef(MainFuncDef mainFuncDef) {
        hasReturnValue = true;
        curTable = curTable.pushScope();//进入新的作用域
        visitBlock(mainFuncDef.block);
        if (mainFuncDef.block.blockItems.isEmpty()) {
            errorManager.handleError(mainFuncDef.block.rbrace.line(), "g");
        } else {
            BlockItem blockItem = mainFuncDef.block.blockItems.get(mainFuncDef.block.blockItems.size() - 1);
            if (!(blockItem instanceof SBlockItem sBlockItem) || !(sBlockItem.stmt instanceof ReturnStmt)) {
                errorManager.handleError(mainFuncDef.block.rbrace.line(), "g");
            }
        }
    }

    private void visitFuncFParams(FuncFParams funcFParams) {

        for (FuncFParam funcFParam : funcFParams.funcFParamList) {
            visitFuncFParam(funcFParam);
        }
    }

    private void visitFuncFParam(FuncFParam funcFParam) {
        SymbolType symbolType = (funcFParam.bType.type.type() == tokenType.INTTK) ? SymbolType.Int : SymbolType.Char;
        boolean isArray = (funcFParam.lbrack != null);

        Symbol symbol = new Symbol(
                curTable.id,
                funcFParam.ident.name(),
                false,
                isArray,
                symbolType
        );

        if (!curTable.addSymbol(symbol)) errorManager.handleError(funcFParam.ident.line(), "b");

    }//FuncFParam → BType Ident ['[' ']']

    private void visitBlock(Block block) {
        for (BlockItem blockItem : block.blockItems) {
            visitBlockItem(blockItem);
        }
        curTable = curTable.popScope();//退出当前作用域
    }

    private void visitBlockItem(BlockItem blockItem) {
        if (blockItem instanceof DBlockItem dBlockItem) {
            visitDecl(dBlockItem.decl);
        } else {
            visitStmt(((SBlockItem) blockItem).stmt);
        }
    }

    private void visitStmt(Stmt stmt) {

        if (stmt instanceof AssignStmt assignStmt) {
            LVal lVal = assignStmt.lVal;
            Symbol symbol = visitLVal(lVal);
            if (isConstant(symbol)) {
                errorManager.handleError(lVal.ident.line(), "h");
            }

            visitExp(assignStmt.exp);

        } else if (stmt instanceof ExpressionStmt expressionStmt) {
            if (expressionStmt.optionalExp != null) {
                visitExp(expressionStmt.optionalExp);
            }

        } else if (stmt instanceof BlockStmt blockStmt) {
            curTable = curTable.pushScope(); // 进入新的作用域
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
            if (loopStmt.cond != null) {
                visitCond(loopStmt.cond);
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
            Symbol symbol = visitLVal(lVal);
            if (isConstant(symbol)) {
                errorManager.handleError(lVal.ident.line(), "h");
            }

        } else if (stmt instanceof GetCharStmt getCharStmt) {
            LVal lVal = getCharStmt.lVal;
            Symbol symbol = visitLVal(lVal);
            if (isConstant(symbol)) {
                errorManager.handleError(lVal.ident.line(), "h");
            }

        } else if (stmt instanceof PrintfStmt printfStmt) {
            int count = 0;
            String s = printfStmt.stringConst.name();
            int len = s.length();
            for (int i = 0; i < len; i++) {
                if (s.charAt(i) == '%' && i + 1 < len && (s.charAt(i + 1) == 'd' || s.charAt(i + 1) == 'c')) {
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
    }

    private void visitForStmt(ForStmt forStmt) {
        LVal lVal = forStmt.lVal;
        Symbol symbol = visitLVal(lVal);
        if (isConstant(symbol)) {
            errorManager.handleError(lVal.ident.line(), "h");
        }
        visitExp(forStmt.exp);
    }

    private Symbol visitExp(Exp exp) {
        return visitAddExp(exp.addExp);
    }

    private void visitCond(Cond cond) {
        visitLOrExp(cond.lOrExp);
    }

    private Symbol visitLVal(LVal lVal) {
        Symbol symbol = curTable.getSymbol(lVal.ident.name());
        if (symbol == null) {
            errorManager.handleError(lVal.ident.line(), "c");
        }
        if (lVal.exp != null) {
            visitExp(lVal.exp);
        }
        if (lVal.lbrack != null && symbol != null) {
            return new Symbol(symbol.getTableId(), symbol.getToken(), symbol.isConst(), false, symbol.getSymbolType());
        }
        return symbol;
    }

    private Symbol visitPrimaryExp(PrimaryExp primaryExp) {
        if (primaryExp instanceof EprimaryExp eprimaryExp) {
            return visitExp(eprimaryExp.exp);
        } else if (primaryExp instanceof LprimaryExp lprimaryExp) {
            return visitLVal(lprimaryExp.lVal);
        } else if (primaryExp instanceof CprimaryExp cprimaryExp) {
            return new Symbol(false, SymbolType.Char);
        } else {
            return new Symbol(false, SymbolType.Int);
        }

    }

    private Symbol visitUnaryExp(UnaryExp unaryExp) {
        if (unaryExp instanceof PunaryExp punaryExp) {
            return visitPrimaryExp(punaryExp.primaryExp);
        } else if (unaryExp instanceof FunaryExp funaryExp) {
            token ident = funaryExp.ident;
            Symbol func = curTable.getSymbol(ident.name());
            if (func == null) {//函数未声明
                errorManager.handleError(ident.line(), "c");
            }

            ArrayList<Symbol> args = new ArrayList<>();
            if (funaryExp.funcRParams != null) {
                visitFuncRParams(funaryExp.funcRParams, args);
            }

            if (func != null) {
                ArrayList<Symbol> params = func.getParamList();
                int paramNum = params.size(), argNum = args.size();
                if (paramNum != argNum) {
                    errorManager.handleError(ident.line(), "d"); // 参数个数不匹配
                } else {
                    for (int i = 0; i < paramNum; i++) {
                        Symbol param = params.get(i), arg = args.get(i);
                        if (param.isArray() != arg.isArray() ||
                                (param.isArray() && arg.isArray() && param.getSymbolType() != arg.getSymbolType())) {
                            errorManager.handleError(ident.line(), "e"); // 参数类型或数组维度不匹配
                            break;
                        }
                    }
                }
            }
            return func;
        } else {
            return visitUnaryExp(((OunaryExp) unaryExp).unaryExp);
        }

    }

    private void visitFuncRParams(FuncRParams funcRParams, ArrayList<Symbol> args) {
        for (Exp exp : funcRParams.exps) {
            args.add(visitExp(exp));
        }
    }

    private Symbol visitMulExp(MulExp mulExp) {
        if (mulExp.mulExp != null) visitMulExp(mulExp.mulExp);
        Symbol symbol = visitUnaryExp(mulExp.unaryExp);
        return mulExp.op != null ? new Symbol(false, SymbolType.Int) : symbol;
    }

    private Symbol visitAddExp(AddExp addExp) {
        if (addExp.addExp != null) visitAddExp(addExp.addExp);
        Symbol symbol = visitMulExp(addExp.mulExp);
        return addExp.op != null ? new Symbol(false, SymbolType.Int) : symbol;
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

    private void write(SymbolTable symbolTable) {
        if (symbolTable != null) {
            for (Map.Entry<String, Symbol> entry : symbolTable.directory.entrySet()) {
                Symbol symbol = entry.getValue();
                myWriter.writeSymbol(symbol);
            }
            for (SymbolTable next : symbolTable.next) {
                write(next);
            }
        }
    }

    public void write() {
        write(globalTable);
    }
}
