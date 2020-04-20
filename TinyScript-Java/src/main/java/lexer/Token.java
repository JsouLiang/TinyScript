package lexer;

public class Token {
    TokenType type;
    String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    /// 是否是变量
    public boolean isVariable() {
        return type == TokenType.VARIABLE;
    }

    /// 是否是值
    public boolean isScalar() {
        return type == TokenType.INTEGER || type == TokenType.FLOAT || type == TokenType.BOOLEAN || type == TokenType.STRING;
    }

    @Override
    public String toString() {
        return String.format("type: %s, value %s", type, value);
    }
}