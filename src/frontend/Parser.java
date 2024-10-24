package frontend;

import frontend.node.blockItem.*;
import frontend.node.constInitVal.*;
import frontend.node.decl.*;
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


public class Parser {
    private token token;
    private final Lexer lexer;
    private int index = 0;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
    }

    private token match() {
        token Token = this.token;
        nextToken();
        return Token;
    }

    private token match(tokenType expected) {
       if(this.token.getType()!=expected){
           return null;
       }
       token Token = this.token;
       nextToken();
       return Token;
    }

    private void nextToken() {
        if (index < lexer.getTokens().size()) {
            this.token = lexer.getTokens().get(index++);  // 消费当前 token，移动到下一个
        }
    }

    private token offset2Token(int offset) {
        return lexer.getTokens().get(index + offset - 1);
    }

    private int tempIndex;

    private void saveContent(){
        tempIndex = this.index;  // 保存当前索引
        errorManager.setHandleErrorFlag(false);
    }

    private void restore(){
        this.index = tempIndex;//恢复索引
        this.token = lexer.getTokens().get(tempIndex-1);//恢复token
        errorManager.setHandleErrorFlag(true);
    }

    public CompUnit parseCompUnit() {
        CompUnit compUnit = new CompUnit();
        // 循环解析 Decl 部分
        while (token.getType() == tokenType.CONSTTK || ((token.getType() == tokenType.INTTK || token.getType() == tokenType.CHARTK) && offset2Token(2).getType() != tokenType.LPARENT)) {
            compUnit.decls.add(parseDecl());
        }// 循环解析 FuncDef 部分
        while (token.getType() == tokenType.VOIDTK || token.getType() == tokenType.CHARTK || (token.getType() == tokenType.INTTK && offset2Token(1).getType() != tokenType.MAINTK)) {
            compUnit.funcDefs.add(parseFuncDef());
        }
        // 解析主函数定义 MainFuncDef
        compUnit.MainFunctionDef = parseMainFuncDef();
        return compUnit;
    }

    private Decl parseDecl() {

        if (token.getType() == tokenType.CONSTTK) {
            CONSTDecl decl=new CONSTDecl();
            decl.constDecl = parseConstDecl();//常量声明
            return decl;
        } else {
            VARDecl decl=new VARDecl();
            decl.varDecl = parseVarDecl();//变量声明
            return decl;
        }
    }

    private ConstDecl parseConstDecl() {
        ConstDecl constDecl = new ConstDecl();
        constDecl._const = match();
        constDecl.bType = parseBType();
        constDecl.constDefs.add(parseConstDef());

        while (token.getType() == tokenType.COMMA) {
            constDecl.comma = match();
            constDecl.constDefs.add(parseConstDef());
        }
        if (token.getType() != tokenType.SEMICN) errorManager.handleError(constDecl.bType.type.getLine(), "i");
        else constDecl.semicn = match();

        return constDecl;
    }

    private ConstDef parseConstDef() {
        ConstDef constDef = new ConstDef();
        constDef.ident = match();
        if (token.getType() == tokenType.LBRACK) {
            constDef.lbrack = match();
            constDef.constExp = parseConstExp();
            if (token.getType() != tokenType.RBRACK) errorManager.handleError(offset2Token(-1).getLine(), "k");
            else constDef.rbrack = match();
        }//是否是一维数组
        constDef.assign = match();//匹配赋值符号
        constDef.constInitVal = parseConstInitVal();//匹配初值
        return constDef;
    }

    private ConstInitVal parseConstInitVal() {

        if (token.getType() == tokenType.LBRACE) {
            ConstExpArrayCIV constInitVal = new ConstExpArrayCIV();
            constInitVal.lbrace = match();
            if (token.getType() != tokenType.RBRACE) {
                constInitVal.constExps.add(parseConstExp());
                while (token.getType() == tokenType.COMMA) {
                    constInitVal.comma = match();
                    constInitVal.constExps.add(parseConstExp());
                }
            }
            constInitVal.rbrace = match();
            return constInitVal;
            //数组初始化
        } else if (token.getType() == tokenType.STRCON) {
            StringConstCIV constInitVal = new StringConstCIV();
            constInitVal.stringConst = match();
            //字符串初始化
            return constInitVal;
        } else {
            ConstExpCIV constInitVal=new ConstExpCIV();
            constInitVal.constExp=parseConstExp();
            //普通变量初始化
            return constInitVal;
        }

    }
    private BType parseBType() {
        BType bType = new BType();
        bType.type = match();
        return bType;
    }

    private VarDecl parseVarDecl() {
        VarDecl varDecl = new VarDecl();
        varDecl.bType = parseBType();
        varDecl.varDefs.add(parseVarDef());
        while (token.getType() == tokenType.COMMA) {
            varDecl.comma = match();
            varDecl.varDefs.add(parseVarDef());
        }
        if (token.getType() != tokenType.SEMICN) errorManager.handleError(varDecl.bType.type.getLine(), "i");
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
        while (token.getType() == tokenType.PLUS || token.getType() == tokenType.MINU) {
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
        while (token.getType() == tokenType.MULT || token.getType() == tokenType.DIV || token.getType() == tokenType.MOD) {
            mulExpPre = mulExp;
            mulExp = new MulExp();
            mulExp.mulExp = mulExpPre;

            mulExp.op = match();

            mulExp.unaryExp = parseUnaryExp();
        }
        return mulExp;
    }

    private UnaryExp parseUnaryExp() {

        if (token.getType() == tokenType.PLUS || token.getType() == tokenType.MINU || token.getType() == tokenType.NOT) {
            UnaryOpUE unaryExp=new UnaryOpUE();
            unaryExp.unaryOp = parseUnaryOp();
            unaryExp.unaryExp = parseUnaryExp();
            return unaryExp;
        } else if (token.getType() == tokenType.IDENFR && offset2Token(1).getType() == tokenType.LPARENT) {
            FuncCallUE unaryExp=new FuncCallUE();
            unaryExp.ident = match();
            unaryExp.lparent = match();

            if (token.getType()==tokenType.LPARENT||token.getType()==tokenType.IDENFR||token.getType()==tokenType.INTCON||token.getType()==tokenType.CHRCON||token.getType()==tokenType.PLUS||token.getType()==tokenType.MINU) unaryExp.funcRParams = parseFuncRParams();//待修改

            if (token.getType() != tokenType.RPARENT){ if(errorManager.handleErrorSwitch())errorManager.handleError(unaryExp.lparent.getLine(), "j");}
            else unaryExp.rparent = match();
            return unaryExp;
        } else {
            PrimaryExpUE unaryExp=new PrimaryExpUE();
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
        while (token.getType() == tokenType.COMMA) {
            funcRParams.comma = match();
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

        if (token.getType() == tokenType.LPARENT) {
            ExpPE primaryExp=new ExpPE();
            primaryExp.lparent = match();
            primaryExp.exp = parseExp();
            if (token.getType() != tokenType.RPARENT){if(errorManager.handleErrorSwitch()) errorManager.handleError(primaryExp.lparent.getLine(), "j");}
            else primaryExp.rparent = match();
            return primaryExp;
        } else if (token.getType() == tokenType.IDENFR) {
            LValPE primaryExp=new LValPE();
            primaryExp.lVal = parseLVal();
            return primaryExp;
        } else if (token.getType() == tokenType.INTCON) {
            NumberPE primaryExp=new NumberPE();
            primaryExp.number = parseNumber();
            return primaryExp;
        } else {
            CharacterPE primaryExp=new CharacterPE();
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
        lVal.ident = match(tokenType.IDENFR);
        if(lVal.ident==null) return null;
        if (token.getType() == tokenType.LBRACK) {
            lVal.lbrack = match();
            lVal.exp = parseExp();

            if (token.getType() != tokenType.RBRACK) {if(errorManager.handleErrorSwitch())errorManager.handleError(offset2Token(-1).getLine(), "k");}
            else lVal.rbrack = match();
        }
        return lVal;
    }

    private VarDef parseVarDef() {
        VarDef varDef = new VarDef();
        varDef.ident = match();
        if (token.getType() == tokenType.LBRACK) {
            varDef.lbrack = match();
            varDef.constExp = parseConstExp();

            if (token.getType() != tokenType.RBRACK) errorManager.handleError(offset2Token(-1).getLine(), "k");
            else varDef.rbrack = match();

        }
        if (token.getType() == tokenType.ASSIGN) {
            varDef.assign = match();
            varDef.initVal = parseInitVal();
        }
        return varDef;
    }

    private InitVal parseInitVal() {
        if (token.getType() == tokenType.LBRACE) {
            ExpArrayIV initVal =new ExpArrayIV();
            initVal.lbrace = match();
            if (token.getType() != tokenType.RBRACE) {
                initVal.exps.add(parseExp());
                while (token.getType() == tokenType.COMMA) {
                    initVal.comma = match();
                    initVal.exps.add(parseExp());
                }
            }
            initVal.rbrace = match();
            return initVal;
            //数组初始化
        } else if (token.getType() == tokenType.STRCON) {
            StringConstIV initVal = new StringConstIV();
            initVal.stringConst = match();
            //字符串初始化
            return initVal;
        } else {
            ExpIV initVal=new ExpIV();
            initVal.exp=parseExp();
            //普通变量初始化
            return initVal;
        }
    }

    private FuncDef parseFuncDef() {
        FuncDef funcDef = new FuncDef();

        funcDef.funcType = parseFuncType();
        funcDef.ident = match();
        funcDef.lparent = match();
        if(token.getType()==tokenType.CHARTK||token.getType()==tokenType.INTTK) funcDef.funcFParams = parseFuncFParams();//待修改

        if (token.getType() != tokenType.RPARENT) errorManager.handleError(funcDef.lparent.getLine(), "j");
        else funcDef.rparent = match();

        funcDef.block = parseBlock();
        return funcDef;
    }

    private Block parseBlock() {
        Block block = new Block();
        block.lbrace = match();
        while (token.getType() != tokenType.RBRACE) {
            block.blockItems.add(parseBlockItem());
        }
        block.rbrace = match();
        return block;
    }

    private BlockItem parseBlockItem() {

        if (token.getType() == tokenType.CONSTTK || token.getType() == tokenType.INTTK || token.getType() == tokenType.CHARTK) {
            DeclBlockItem blockItem=new DeclBlockItem();
            blockItem.decl = parseDecl();
            return blockItem;
        } else {
            StmtBlockItem blockItem=new StmtBlockItem();
            blockItem.stmt = parseStmt();
            return blockItem;
        }

    }

    private Stmt parseStmt() {
        if (token.getType() == tokenType.IFTK) {
            IfStmt stmt=new IfStmt();
            stmt.ifToken = match();
            stmt.lparent = match();
            stmt.cond = parseCond();

            if (token.getType() != tokenType.RPARENT) errorManager.handleError(stmt.lparent.getLine(), "j");
            else stmt.rparent = match();

            stmt.thenStmt = parseStmt();
            if (token.getType() == tokenType.ELSETK) {
                stmt.elseToken = match();
                stmt.elseStmt = parseStmt();
            }
            return stmt;
        } else if (token.getType() == tokenType.FORTK) {
            LoopStmt stmt=new LoopStmt();
            stmt.forToken = match();
            stmt.lparent = match();
            if (token.getType() != tokenType.SEMICN) {
                stmt.initForStmt = parseForStmt();
            }
            stmt.semicn1 = match();
            if (token.getType() != tokenType.SEMICN) {
                stmt.forCondition = parseCond();
            }
            stmt.semicn2 = match();
            if (token.getType() != tokenType.RPARENT) {
                stmt.updateForStmt = parseForStmt();
            }
            stmt.rparent = match();
            stmt.forBody = parseStmt();

            return stmt;
        } else if (token.getType() == tokenType.BREAKTK) {
            BreakStmt stmt=new BreakStmt();
            stmt.breakToken = match();

            if (token.getType() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).getLine(), "i");
            else stmt.semicn = match();
           
            return stmt;
        } else if (token.getType() == tokenType.CONTINUETK) {
            ContinueStmt stmt=new ContinueStmt();
            stmt.continueToken = match();

            if (token.getType() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).getLine(), "i");
            else stmt.semicn = match();
        
            return stmt;
        } else if (token.getType() == tokenType.RETURNTK) {
            ReturnStmt stmt=new ReturnStmt();
            stmt.returnToken = match();
            if (token.getType()==tokenType.LPARENT||token.getType()==tokenType.IDENFR||token.getType()==tokenType.INTCON||token.getType()==tokenType.CHRCON||token.getType()==tokenType.PLUS||token.getType()==tokenType.MINU) {
                stmt.returnExp = parseExp();
            }

            if (token.getType() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).getLine(), "i");
            else stmt.semicn = match();
      
            return stmt;
        } else if (token.getType() == tokenType.PRINTFTK) {
            PrintfStmt stmt=new PrintfStmt();
            stmt.printf = match();
            stmt.lparent = match();
            stmt.stringConst = match();
            while (token.getType() == tokenType.COMMA) {
                stmt.comma = match();
                stmt.exps.add(parseExp());
            }

            if (token.getType() != tokenType.RPARENT) errorManager.handleError(stmt.lparent.getLine(), "j");
            else stmt.rparent = match();

            if (token.getType() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).getLine(), "i");
            else stmt.semicn = match();

            return stmt;
        } else if (token.getType() == tokenType.LBRACE) {
            //block
            BlockStmt stmt=new BlockStmt();
            stmt.block = parseBlock();
            return stmt;
        } else {

            //LVal '=' Exp ';' // h i
            //LVal '=' 'getint''('')'';' // h i j
            //LVal '=' 'getchar''('')'';' // h i j

            saveContent();

            if(parseLVal()!=null&&token.getType()==tokenType.ASSIGN){

                tokenType nextType=offset2Token(1).getType();

                restore();

                if(nextType==tokenType.GETINTTK){
                    GetIntStmt stmt=new GetIntStmt();
                    stmt.lVal = parseLVal();
                    stmt.assign = match();
                    stmt.getint = match();
                    stmt.lparent = match();

                    if (token.getType() != tokenType.RPARENT) errorManager.handleError(stmt.lparent.getLine(), "j");
                    else stmt.rparent = match();

                    if (token.getType() != tokenType.SEMICN) errorManager.handleError(stmt.lparent.getLine(), "i");
                    else stmt.semicn = match();

                    return stmt;
                } else if (nextType==tokenType.GETCHARTK) {
                    GetCharStmt stmt=new GetCharStmt();
                    stmt.lVal = parseLVal();
                    stmt.assign = match();
                    stmt.getchar = match();
                    stmt.lparent = match();

                    if (token.getType() != tokenType.RPARENT) errorManager.handleError(stmt.lparent.getLine(), "j");
                    else stmt.rparent = match();

                    if (token.getType() != tokenType.SEMICN) errorManager.handleError(stmt.lparent.getLine(), "i");
                    else stmt.semicn = match();

                    return stmt;
                }else {
                    AssignStmt stmt=new AssignStmt();
                    stmt.lVal = parseLVal();
                    stmt.assign = match();
                    stmt.exp = parseExp();

                    if (token.getType() != tokenType.SEMICN) errorManager.handleError(stmt.assign.getLine(), "i");
                    else stmt.semicn = match();

                    return stmt;
                }
            }else{
                //[exp];
                // '(' Exp ')' | LVal | Number | Character | Ident '(' [FuncRParams] ')' | UnaryOp UnaryExp
                ExpressionStmt stmt=new ExpressionStmt();

                restore();

                if (token.getType()==tokenType.LPARENT||token.getType()==tokenType.IDENFR||token.getType()==tokenType.INTCON||token.getType()==tokenType.CHRCON||token.getType()==tokenType.PLUS||token.getType()==tokenType.MINU) {
                    stmt.optionalExp = parseExp();
                }

                if (token.getType() != tokenType.SEMICN) errorManager.handleError(offset2Token(-1).getLine(), "i");
                else stmt.semicn = match();

                return stmt;
            }
        }
    }//难，可能出bug

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
        while (token.getType() == tokenType.OR) {
            lOrExpPre = lOrExp;
            lOrExp = new LOrExp();
            lOrExp.lOrExp = lOrExpPre;
            lOrExp.or = match();
            if (lOrExp.or.getToken().equals("|")) errorManager.handleError(lOrExp.or.getLine(), "a");
            lOrExp.lAndExp = parseLAndExp();
        }
        return lOrExp;
    }

    private LAndExp parseLAndExp() {
        LAndExp lAndExp = new LAndExp(), lAndExpPre;
        lAndExp.eqExp = parseEqExp();
        while (token.getType() == tokenType.AND) {
            lAndExpPre = lAndExp;
            lAndExp = new LAndExp();
            lAndExp.lAndExp = lAndExpPre;
            lAndExp.and = match();
            if (lAndExp.and.getToken().equals("&")) errorManager.handleError(lAndExp.and.getLine(), "a");
            lAndExp.eqExp = parseEqExp();
        }
        return lAndExp;
    }

    private EqExp parseEqExp() {
        EqExp eqExp = new EqExp(), eqExpPre;
        eqExp.relExp = parseRelExp();
        while (token.getType() == tokenType.EQL || token.getType() == tokenType.NEQ) {
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
        while (token.getType() == tokenType.LSS || token.getType() == tokenType.LEQ || token.getType() == tokenType.GRE || token.getType() == tokenType.GEQ) {
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
        while (token.getType() == tokenType.COMMA) {
            funcFParams.comma = match();
            funcFParams.funcFParams.add(parseFuncFParam());
        }
        return funcFParams;
    }

    private FuncFParam parseFuncFParam() {
        FuncFParam funcFParam = new FuncFParam();
        funcFParam.bType = parseBType();
        funcFParam.ident = match();
        if (token.getType() == tokenType.LBRACK) {
            funcFParam.lbrack = match();

            if (token.getType() != tokenType.RBRACK) errorManager.handleError(offset2Token(-1).getLine(), "k");
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

        if (token.getType() != tokenType.RPARENT) errorManager.handleError(mainFuncDef.lparent.getLine(), "j");
        else mainFuncDef.rparent = match();

        mainFuncDef.block = parseBlock();
        return mainFuncDef;
    }

}


