package lexer;

public class LexicalException extends Exception {
    private String message;

    public LexicalException(char c) {
        message = String.format("Unexpected character %c", c);
    }

    public LexicalException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
