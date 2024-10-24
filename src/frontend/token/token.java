package frontend.token;


import frontend.tool.myWriter;

public record token(String token, tokenType type, int line) {

    public void visit() {
        myWriter.writeTerminal(this);
    }
}