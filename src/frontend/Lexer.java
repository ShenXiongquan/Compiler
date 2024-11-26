package frontend;

import frontend.token.token;
import frontend.token.tokenType;
import frontend.tool.myWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Lexer {


    private int lineNum = 1;

    private final PushbackReader reader;

    private final List<token> tokens = new ArrayList<>();

    public List<token> getTokens() {
        token token;
        while (true) {
            try {
                if ((token = nextToken()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            tokens.add(token);
        }
        return tokens;
    }

    public Lexer(PushbackReader reader) {
        this.reader = reader;
    }

    public token nextToken() throws IOException {
        char c;
        while ((c = (char) reader.read()) != (char) -1) {
            if (Character.isWhitespace(c)) {
                if (c == '\n') lineNum++;
                continue;
            }//跳过空白符
            else if (c == '/') {
                if (skipComments()) continue;
            }//跳过注释

            StringBuilder currentToken = new StringBuilder();
            currentToken.append(c);

            if (c == '\"') {
                return handleStrConst(currentToken);
            } else if (c == '\'') {
                return handleCharConst(currentToken);
            } else if (Character.isDigit(c)) {
                return handleIntConst(currentToken);
            } else if (c == '_' || Character.isLetter(c)) {
                return handleIdentifier(currentToken);
            } else {
                return handleOperator(currentToken, c);
            }
        }
        return null;
    }

    private boolean skipComments() throws IOException {
        char c = (char) reader.read();
        if (c == '/') {
            while ((c = (char) reader.read()) != (char) -1) {
                if (c == '\n') {
                    lineNum++;
                    break;
                }
            }
            return true;
        } else if (c == '*') {
            while ((c = (char) reader.read()) != (char) -1) {
                if (c == '\n') lineNum++;
                else if (c == '*') {
                    if ((c = (char) reader.read()) == '/') break;
                    else reader.unread(c);
                }
            }
            return true;
        } else {
            reader.unread(c);
            return false;
        }
    }

    private token handleStrConst(StringBuilder currentToken) throws IOException {
        while (true) {
            int currentChar = reader.read();
            if (currentChar == -1) break;
            char c = (char) currentChar;
            currentToken.append(c);
            if (c == '\\') {
                c = (char) reader.read();
                currentToken.append(c);
                continue;
            }
            if (c == '\"') {
                return new token(currentToken.toString(), tokenType.STRCON, lineNum);
            }
        }
        return null;
    }

    private token handleCharConst(StringBuilder currentToken) throws IOException {
        char c = (char) reader.read();
        currentToken.append(c);
        if (c == '\\') {
            c = (char) reader.read();
            currentToken.append(c);
        }

        c = (char) reader.read();
        currentToken.append(c);
        return new token(currentToken.toString(), tokenType.CHRCON, lineNum);
    }

    private token handleIntConst(StringBuilder currentToken) throws IOException {
        while (true) {
            int currentChar = reader.read();

            if (currentChar == -1) {
                // 处理流结束的情况
                if (currentToken.length() > 0) {
                    return new token(currentToken.toString(), tokenType.INTCON, lineNum);
                } else {
                    return null;
                }
            }

            char c = (char) currentChar;
            if (Character.isDigit(c)) {
                currentToken.append(c);
            } else {
                reader.unread(c);
                return new token(currentToken.toString(), tokenType.INTCON, lineNum);
            }
        }
    }

    private token handleIdentifier(StringBuilder currentToken) throws IOException {
        while (true) {
            int currentChar = reader.read();

            if (currentChar == -1) {
                // 处理流结束的情况
                if (currentToken.length() > 0) {
                    return new token(currentToken.toString(), Objects.requireNonNullElse(tokenType.getTokenType(currentToken.toString()), tokenType.IDENFR), lineNum);
                } else {
                    return null;
                }
            }

            char c = (char) currentChar;
            if (Character.isLetterOrDigit(c) || c == '_') {
                currentToken.append(c);
            } else {
                reader.unread(c);
                return new token(currentToken.toString(), Objects.requireNonNullElse(tokenType.getTokenType(currentToken.toString()), tokenType.IDENFR), lineNum);
            }
        }
    }

    private token handleOperator(StringBuilder currentToken, char c) throws IOException {
        switch (c) {
            case '&':
                c = (char) reader.read();
                if (c == '&') {
                    currentToken.append(c);
                    return new token(currentToken.toString(), tokenType.AND, lineNum);
                } else {
                    reader.unread(c);
                    return new token("&", tokenType.AND, lineNum);
                }
            case '|':
                c = (char) reader.read();
                if (c == '|') {
                    currentToken.append(c);
                    return new token(currentToken.toString(), tokenType.OR, lineNum);
                } else {
                    reader.unread(c);
                    return new token("|", tokenType.OR, lineNum);
                }
            case '!':
            case '>':
            case '<':
            case '=':
                c = (char) reader.read();
                if (c == '=') {
                    currentToken.append(c);
                } else {
                    reader.unread(c);
                }
                return new token(currentToken.toString(), tokenType.getTokenType(currentToken.toString()), lineNum);
            default:
                return new token(currentToken.toString(), tokenType.getTokenType(currentToken.toString()), lineNum);
        }
    }

    public void write() {
        for (frontend.token.token token : tokens) {
            myWriter.writeTerminal(token);
        }

    }
}


