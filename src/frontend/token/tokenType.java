package frontend.token;

public enum tokenType {
    MAINTK("main"),
    CONSTTK("const"),
    INTTK("int"),
    CHARTK("char"),
    BREAKTK("break"),
    CONTINUETK("continue"),
    IFTK("if"),
    ELSETK("else"),
    NOT("!"),
    AND("&&"),
    OR("||"),
    FORTK("for"),
    GETINTTK("getint"),
    GETCHARTK("getchar"),
    PRINTFTK("printf"),
    RETURNTK("return"),
    PLUS("+"),
    MINU("-"),
    VOIDTK("void"),
    MULT("*"),
    DIV("/"),
    MOD("%"),
    LSS("<"),
    LEQ("<="),
    GRE(">"),
    GEQ(">="),
    EQL("=="),
    NEQ("!="),
    ASSIGN("="),
    SEMICN(";"),
    COMMA(","),
    LPARENT("("),
    RPARENT(")"),
    LBRACK("["),
    RBRACK("]"),
    LBRACE("{"),
    RBRACE("}"),
    IDENFR,
    INTCON,
    STRCON,
    CHRCON;

    private String representation = null;

    tokenType(String representation) {
        this.representation = representation;
    }

    tokenType() {}

    public static tokenType getTokenType(String token) {
        for (tokenType tokenType : tokenType.values()) {
            String rep = tokenType.representation;
            if (rep == null) return null;
            if (rep.equals(token)) {
                return tokenType;
            }
        }
        return null;
    }
}
