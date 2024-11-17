package frontend.token;


import frontend.tool.myWriter;

public record token(String token, tokenType type, int line) {

    public void print() {
        myWriter.writeTerminal(this);
    }

}