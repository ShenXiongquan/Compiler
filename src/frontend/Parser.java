package frontend;

import frontend.node.blockItem.*;
import frontend.node.constInitVal.*;
import frontend.node.decl.*;
import frontend.node.decl.ConstDecl;
import frontend.node.decl.VarDecl;
import frontend.node.initVal.*;
import frontend.node.primaryExp.*;
import frontend.node.stmt.*;
import frontend.node.unaryExp.*;
import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.errorManager;
import frontend.node.*;
import frontend.node.Character;
import frontend.node.Number;

import java.util.List;


public class Parser {
 
    private final List<token> tokens;
    private int index = 0;//索引和当前的token保持一致
    private token token;
    public Parser(List<token> tokens) {
        this.tokens= tokens;
        this.token =tokens.get(index);
    }

    //匹配所给的终结符
    private token match() {
        token Token = this.token;
        if (index < tokens.size()-1) {
            this.token = tokens.get(++index); 
        }
        return Token;
    }

    private token offset2Token(int offset) {
        return tokens.get(index + offset);
    }
    
    private int tempIndex;//回溯保存索引值

    private void saveContent() {
        tempIndex = this.index;  // 保存当前索引
    }

    private void restore() {
        this.index = tempIndex;//恢复索引
        this.token = tokens.get(tempIndex);//恢复token
    }

    public CompUnit parseCompUnit() {
        CompUnit compUnit = new CompUnit();

        while (token.type() == tokenType.CONSTTK || ((token.type() == tokenType.INTTK || token.type() == tokenType.CHARTK) && offset2Token(2).type() != tokenType.LPARENT)) {
            compUnit.decls.add(parseDecl());
        }
        while (token.type() == tokenType.VOIDTK || token.type() == tokenType.CHARTK || (token.type() == tokenType.INTTK && offset2Token(1).type() != tokenType.MAINTK)) {
            compUnit.funcDefs.add(parseFuncDef());
        }

        compUnit.MainFunctionDef = parseMainFuncDef();
        return compUnit;
    }

    private Decl parseDecl() {

        if (token.type() == tokenType.CONSTTK) {
            ConstDecl decl = new ConstDecl();
            decl.constDecl = parseConstDecl();//常量声明
            return decl;
        } else {
            VarDecl decl = new VarDecl();
            decl.varDecl = parseVarDecl();//变量声明
            return decl;
        }
    }

    private frontend.node.ConstDecl parseConstDecl() {
        frontend.node.ConstDecl constDecl = new frontend.node.ConstDecl();
        constDecl._const = match();
        constDecl.bType = parseBType();
        constDecl.constDefs.add(parseConstDef());

        while (token.type() == tokenType.COMMA) {
            constDecl.comma.add(match());
            constDecl.constDefs.add(parseConstDef());
        }
        if (token.type() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).line(), "i");
        else constDecl.semicn = match();

        return constDecl;
    }

    private ConstDef parseConstDef() {
        ConstDef constDef = new ConstDef();
        constDef.ident = match();
        if (token.type() == tokenType.LBRACK) {
            constDef.lbrack = match();
            constDef.constExp = parseConstExp();
            if (token.type() != tokenType.RBRACK) errorManager.handleError(offset2Token(-1).line(), "k");
            else constDef.rbrack = match();
        }//是否是一维数组
        constDef.assign = match();//匹配赋值符号
        constDef.constInitVal = parseConstInitVal();//匹配初值
        return constDef;
    }

    private ConstInitVal parseConstInitVal() {

        if (token.type() == tokenType.LBRACE) {
            ArrayConstInitVal constInitVal = new ArrayConstInitVal();
            constInitVal.lbrace = match();
            if (token.type() != tokenType.RBRACE) {
                constInitVal.constExps.add(parseConstExp());
                while (token.type() == tokenType.COMMA) {
                    constInitVal.comma = match();
                    constInitVal.constExps.add(parseConstExp());
                }
            }
            constInitVal.rbrace = match();
            return constInitVal;
            //数组初始化
        } else if (token.type() == tokenType.STRCON) {
            StringConstInitVal constInitVal = new StringConstInitVal();
            constInitVal.stringConst = match();
            //字符串初始化
            return constInitVal;
        } else {
            ExpConstInitVal constInitVal = new ExpConstInitVal();
            constInitVal.constExp = parseConstExp();
            //普通变量初始化
            return constInitVal;
        }

    }

    private BType parseBType() {
        BType bType = new BType();
        bType.type = match();
        return bType;
    }

    private frontend.node.VarDecl parseVarDecl() {
        frontend.node.VarDecl varDecl = new frontend.node.VarDecl();
        varDecl.bType = parseBType();
        varDecl.varDefs.add(parseVarDef());
        while (token.type() == tokenType.COMMA) {
            varDecl.comma.add(match());
            varDecl.varDefs.add(parseVarDef());
        }
        if (token.type() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).line(), "i");
        else varDecl.semicn = match();
        return varDecl;
    }

    private ConstExp parseConstExp() {
        ConstExp constExp = new ConstExp();
        constExp.addExp = parseAddExp();
        return constExp;
    }

    private AddExp parseAddExp() {
        AddExp addExp = new AddExp(), addExpPre;
        addExp.mulExp = parseMulExp();
        while (token.type() == tokenType.PLUS || token.type() == tokenType.MINU) {
            addExpPre = addExp;
            addExp = new AddExp();
            addExp.addExp = addExpPre;

            addExp.op = match();

            addExp.mulExp = parseMulExp();
        }
        return addExp;
    }

    private MulExp parseMulExp() {
        MulExp mulExp = new MulExp(), mulExpPre;
        mulExp.unaryExp = parseUnaryExp();
        while (token.type() == tokenType.MULT || token.type() == tokenType.DIV || token.type() == tokenType.MOD) {
            mulExpPre = mulExp;
            mulExp = new MulExp();
            mulExp.mulExp = mulExpPre;

            mulExp.op = match();

            mulExp.unaryExp = parseUnaryExp();
        }
        return mulExp;
    }

    private UnaryExp parseUnaryExp() {

        if (token.type() == tokenType.PLUS || token.type() == tokenType.MINU || token.type() == tokenType.NOT) {
            UnaryOpUE unaryExp = new UnaryOpUE();
            unaryExp.unaryOp = parseUnaryOp();
            unaryExp.unaryExp = parseUnaryExp();
            return unaryExp;
        } else if (token.type() == tokenType.IDENFR && offset2Token(1).type() == tokenType.LPARENT) {
            FuncCallUE unaryExp = new FuncCallUE();
            unaryExp.ident = match();
            unaryExp.lparent = match();

            if (token.type() == tokenType.LPARENT || token.type() == tokenType.IDENFR || token.type() == tokenType.INTCON || token.type() == tokenType.CHRCON || token.type() == tokenType.PLUS || token.type() == tokenType.MINU)
                unaryExp.funcRParams = parseFuncRParams();//待修改

            if (token.type() != tokenType.RPARENT) {
                 errorManager.handleError(offset2Token(-1).line(), "j");
            } else unaryExp.rparent = match();
            return unaryExp;
        } else {
            PrimaryExpUE unaryExp = new PrimaryExpUE();
            unaryExp.primaryExp = parsePrimaryExp();
            return unaryExp;
        }
    }

    private UnaryOp parseUnaryOp() {
        UnaryOp unaryOp = new UnaryOp();

        unaryOp.op = match();

        return unaryOp;
    }

    private FuncRParams parseFuncRParams() {
        FuncRParams funcRParams = new FuncRParams();
        funcRParams.exps.add(parseExp());
        while (token.type() == tokenType.COMMA) {
            funcRParams.comma.add(match());
            funcRParams.exps.add(parseExp());
        }
        return funcRParams;
    }

    private Exp parseExp() {
        Exp exp = new Exp();
        exp.addExp = parseAddExp();
        return exp;
    }

    private PrimaryExp parsePrimaryExp() {

        if (token.type() == tokenType.LPARENT) {
            ExpPE primaryExp = new ExpPE();
            primaryExp.lparent = match();
            primaryExp.exp = parseExp();
            if (token.type() != tokenType.RPARENT) {
                errorManager.handleError(offset2Token(-1).line(), "j");
            } else primaryExp.rparent = match();
            return primaryExp;
        } else if (token.type() == tokenType.IDENFR) {
            LValPE primaryExp = new LValPE();
            primaryExp.lVal = parseLVal();
            return primaryExp;
        } else if (token.type() == tokenType.INTCON) {
            NumberPE primaryExp = new NumberPE();
            primaryExp.number = parseNumber();
            return primaryExp;
        } else {
            CharacterPE primaryExp = new CharacterPE();
            primaryExp.character = parseCharacter();
            return primaryExp;
        }
    }

    private Character parseCharacter() {
        Character character = new Character();
        character.charConst = match();
        return character;
    }

    private Number parseNumber() {
        Number number = new Number();
        number.intConst = match();
        return number;
    }

    private LVal parseLVal() {
        LVal lVal = new LVal();
        lVal.ident = match();
        if (token.type() == tokenType.LBRACK) {
            lVal.lbrack = match();
            lVal.exp = parseExp();

            if (token.type() != tokenType.RBRACK) {
                errorManager.handleError(offset2Token(-1).line(), "k");
            } else lVal.rbrack = match();
        }
        return lVal;
    }

    private VarDef parseVarDef() {
        VarDef varDef = new VarDef();
        varDef.ident = match();
        if (token.type() == tokenType.LBRACK) {
            varDef.lbrack = match();
            varDef.constExp = parseConstExp();

            if (token.type() != tokenType.RBRACK) errorManager.handleError(offset2Token(-1).line(), "k");
            else varDef.rbrack = match();

        }
        if (token.type() == tokenType.ASSIGN) {
            varDef.assign = match();
            varDef.initVal = parseInitVal();
        }
        return varDef;
    }

    private InitVal parseInitVal() {
        if (token.type() == tokenType.LBRACE) {
            ArrayInitVal initVal = new ArrayInitVal();
            initVal.lbrace = match();
            if (token.type() != tokenType.RBRACE) {
                initVal.exps.add(parseExp());
                while (token.type() == tokenType.COMMA) {
                    initVal.comma = match();
                    initVal.exps.add(parseExp());
                }
            }
            initVal.rbrace = match();
            return initVal;
            //数组初始化
        } else if (token.type() == tokenType.STRCON) {
            StringInitVal initVal = new StringInitVal();
            initVal.stringConst = match();
            //字符串初始化
            return initVal;
        } else {
            ExpInitVal initVal = new ExpInitVal();
            initVal.exp = parseExp();
            //普通变量初始化
            return initVal;
        }
    }

    private FuncDef parseFuncDef() {
        FuncDef funcDef = new FuncDef();

        funcDef.funcType = parseFuncType();
        funcDef.ident = match();
        funcDef.lparent = match();
        if (token.type() == tokenType.CHARTK || token.type() == tokenType.INTTK)
            funcDef.funcFParams = parseFuncFParams();//待修改

        if (token.type() != tokenType.RPARENT) errorManager.handleError(offset2Token(-1).line(), "j");
        else funcDef.rparent = match();

        funcDef.block = parseBlock();
        return funcDef;
    }

    private Block parseBlock() {
        Block block = new Block();
        block.lbrace = match();
        while (token.type() != tokenType.RBRACE) {
            block.blockItems.add(parseBlockItem());
        }
        block.rbrace = match();
        return block;
    }

    private BlockItem parseBlockItem() {

        if (token.type() == tokenType.CONSTTK || token.type() == tokenType.INTTK || token.type() == tokenType.CHARTK) {
            DeclBlockItem blockItem = new DeclBlockItem();
            blockItem.decl = parseDecl();
            return blockItem;
        } else {
            StmtBlockItem blockItem = new StmtBlockItem();
            blockItem.stmt = parseStmt();
            return blockItem;
        }

    }

    private Stmt parseStmt() {
        if (token.type() == tokenType.IFTK) {
            IfStmt stmt = new IfStmt();
            stmt.ifToken = match();
            stmt.lparent = match();
            stmt.cond = parseCond();

            if (token.type() != tokenType.RPARENT) errorManager.handleError(offset2Token(-1).line(), "j");
            else stmt.rparent = match();

            stmt.thenStmt = parseStmt();
            if (token.type() == tokenType.ELSETK) {
                stmt.elseToken = match();
                stmt.elseStmt = parseStmt();
            }
            return stmt;
        } else if (token.type() == tokenType.FORTK) {
            LoopStmt stmt = new LoopStmt();
            stmt.forToken = match();
            stmt.lparent = match();
            if (token.type() != tokenType.SEMICN) {
                stmt.initForStmt = parseForStmt();
            }
            stmt.semicn1 = match();
            if (token.type() != tokenType.SEMICN) {
                stmt.forCondition = parseCond();
            }
            stmt.semicn2 = match();
            if (token.type() != tokenType.RPARENT) {
                stmt.updateForStmt = parseForStmt();
            }
            stmt.rparent = match();
            stmt.forBody = parseStmt();

            return stmt;
        } else if (token.type() == tokenType.BREAKTK) {
            BreakStmt stmt = new BreakStmt();
            stmt.breakToken = match();

            if (token.type() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).line(), "i");
            else stmt.semicn = match();

            return stmt;
        } else if (token.type() == tokenType.CONTINUETK) {
            ContinueStmt stmt = new ContinueStmt();
            stmt.continueToken = match();

            if (token.type() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).line(), "i");
            else stmt.semicn = match();

            return stmt;
        } else if (token.type() == tokenType.RETURNTK) {
            ReturnStmt stmt = new ReturnStmt();
            stmt.returnToken = match();
            if (token.type() == tokenType.LPARENT || token.type() == tokenType.IDENFR || token.type() == tokenType.INTCON || token.type() == tokenType.CHRCON || token.type() == tokenType.PLUS || token.type() == tokenType.MINU) {
                stmt.returnExp = parseExp();
            }

            if (token.type() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).line(), "i");
            else stmt.semicn = match();

            return stmt;
        } else if (token.type() == tokenType.PRINTFTK) {
            PrintfStmt stmt = new PrintfStmt();
            stmt.printf = match();
            stmt.lparent = match();
            stmt.stringConst = match();
            while (token.type() == tokenType.COMMA) {
                stmt.comma = match();
                stmt.exps.add(parseExp());
            }

            if (token.type() != tokenType.RPARENT) errorManager.handleError(offset2Token(-1).line(), "j");
            else stmt.rparent = match();

            if (token.type() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).line(), "i");
            else stmt.semicn = match();

            return stmt;
        } else if (token.type() == tokenType.LBRACE) {
            //block
            BlockStmt stmt = new BlockStmt();
            stmt.block = parseBlock();
            return stmt;
        } else {
            //LVal '=' Exp ';' // h i
            //LVal '=' 'getint''('')'';' // h i j
            //LVal '=' 'getchar''('')'';' // h i j
            saveContent();
            parseLVal();
            if (token.type() == tokenType.ASSIGN) {

                tokenType nextType = offset2Token(1).type();

                restore();

                if (nextType == tokenType.GETINTTK) {
                    GetIntStmt stmt = new GetIntStmt();
                    stmt.lVal = parseLVal();
                    stmt.assign = match();
                    stmt.getint = match();
                    stmt.lparent = match();

                    if (token.type() != tokenType.RPARENT) errorManager.handleError(offset2Token(-1).line(), "j");
                    else stmt.rparent = match();

                    if (token.type() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).line(), "i");
                    else stmt.semicn = match();

                    return stmt;
                } else if (nextType == tokenType.GETCHARTK) {
                    GetCharStmt stmt = new GetCharStmt();
                    stmt.lVal = parseLVal();
                    stmt.assign = match();
                    stmt.getchar = match();
                    stmt.lparent = match();

                    if (token.type() != tokenType.RPARENT) errorManager.handleError(offset2Token(-1).line(), "j");
                    else stmt.rparent = match();

                    if (token.type() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).line(), "i");
                    else stmt.semicn = match();

                    return stmt;
                } else {
                    AssignStmt stmt = new AssignStmt();
                    stmt.lVal = parseLVal();
                    stmt.assign = match();
                    stmt.exp = parseExp();

                    if (token.type() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).line(), "i");
                    else stmt.semicn = match();

                    return stmt;
                }
            } else {
                //[exp];
                // '(' Exp ')' | LVal | Number | Character | Ident '(' [FuncRParams] ')' | UnaryOp UnaryExp
                restore();

                ExpressionStmt stmt = new ExpressionStmt();
                if (token.type() == tokenType.LPARENT || token.type() == tokenType.IDENFR || token.type() == tokenType.INTCON || token.type() == tokenType.CHRCON || token.type() == tokenType.PLUS || token.type() == tokenType.MINU) {
                    stmt.optionalExp = parseExp();
                }

                if (token.type() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).line(), "i");
                else stmt.semicn = match();

                return stmt;
            }
        }
    }

    private ForStmt parseForStmt() {
        ForStmt forStmt = new ForStmt();
        forStmt.lVal = parseLVal();
        forStmt.assign = match();
        forStmt.exp = parseExp();
        return forStmt;
    }

    private Cond parseCond() {
        Cond cond = new Cond();
        cond.lOrExp = parseLOrExp();
        return cond;
    }

    private LOrExp parseLOrExp() {
        LOrExp lOrExp = new LOrExp(), lOrExpPre;
        lOrExp.lAndExp = parseLAndExp();
        while (token.type() == tokenType.OR) {
            lOrExpPre = lOrExp;
            lOrExp = new LOrExp();
            lOrExp.lOrExp = lOrExpPre;
            lOrExp.or = match();
            if (lOrExp.or.token().equals("|")) errorManager.handleError(lOrExp.or.line(), "a");
            lOrExp.lAndExp = parseLAndExp();
        }
        return lOrExp;
    }

    private LAndExp parseLAndExp() {
        LAndExp lAndExp = new LAndExp(), lAndExpPre;
        lAndExp.eqExp = parseEqExp();
        while (token.type() == tokenType.AND) {
            lAndExpPre = lAndExp;
            lAndExp = new LAndExp();
            lAndExp.lAndExp = lAndExpPre;
            lAndExp.and = match();
            if (lAndExp.and.token().equals("&")) errorManager.handleError(lAndExp.and.line(), "a");
            lAndExp.eqExp = parseEqExp();
        }
        return lAndExp;
    }

    private EqExp parseEqExp() {
        EqExp eqExp = new EqExp(), eqExpPre;
        eqExp.relExp = parseRelExp();
        while (token.type() == tokenType.EQL || token.type() == tokenType.NEQ) {
            eqExpPre = eqExp;
            eqExp = new EqExp();
            eqExp.eqExp = eqExpPre;

            eqExp.op = match();

            eqExp.relExp = parseRelExp();
        }
        return eqExp;
    }

    private RelExp parseRelExp() {
        RelExp relExp = new RelExp(), relExpPre;
        relExp.addExp = parseAddExp();
        while (token.type() == tokenType.LSS || token.type() == tokenType.LEQ || token.type() == tokenType.GRE || token.type() == tokenType.GEQ) {
            relExpPre = relExp;
            relExp = new RelExp();
            relExp.relExp = relExpPre;
            relExp.relOp = match();
            relExp.addExp = parseAddExp();
        }
        return relExp;
    }

    private FuncFParams parseFuncFParams() {
        FuncFParams funcFParams = new FuncFParams();
        funcFParams.funcFParams.add(parseFuncFParam());
        while (token.type() == tokenType.COMMA) {
            funcFParams.comma.add(match()) ;
            funcFParams.funcFParams.add(parseFuncFParam());
        }
        return funcFParams;
    }

    private FuncFParam parseFuncFParam() {
        FuncFParam funcFParam = new FuncFParam();
        funcFParam.bType = parseBType();
        funcFParam.ident = match();
        if (token.type() == tokenType.LBRACK) {
            funcFParam.lbrack = match();

            if (token.type() != tokenType.RBRACK) errorManager.handleError(offset2Token(-1).line(), "k");
            else funcFParam.rbrack = match();

        }
        return funcFParam;
    }

    private FuncType parseFuncType() {
        FuncType funcType = new FuncType();
        funcType.returnType = match();
        return funcType;
    }

    private MainFuncDef parseMainFuncDef() {
        MainFuncDef mainFuncDef = new MainFuncDef();
        mainFuncDef.intToken = match();
        mainFuncDef.main = match();
        mainFuncDef.lparent = match();

        if (token.type() != tokenType.RPARENT) errorManager.handleError(offset2Token(-1).line(), "j");
        else mainFuncDef.rparent = match();

        mainFuncDef.block = parseBlock();
        return mainFuncDef;
    }

}


