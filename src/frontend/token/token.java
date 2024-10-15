package frontend.token;


import frontend.tool.myWriter;

public class token {
    private final String token;
    private final tokenType type;
    private final int line;

    public token(String token, tokenType type, int line) {
        this.token = token;
        this.type = type;
        this.line = line;
    }

    public String getToken() {
        return token;
    }

    public int getLine() {
        return line;
    }

    public tokenType getType() {
        return type;
    }

    public void visit() {
        myWriter.writeTerminal(this);
    }
}