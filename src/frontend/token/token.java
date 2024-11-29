package frontend.token;


public record token(String name, tokenType type, int line) {

    public String print() {
        return type + " " + name + "\n";
    }

}