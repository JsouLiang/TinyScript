package parse.util;

import lexer.Token;

public class ParseException extends Exception {
    private String msg;

    public ParseException(String msg) {
        this.msg = msg;
    }

    public ParseException(Token token) {
        msg = String.format("Syntax Error, unexpected token %s", token.getValue());
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
