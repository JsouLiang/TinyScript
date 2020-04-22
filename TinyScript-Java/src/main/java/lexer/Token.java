package lexer;

import common.PeekIterator;

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

    /**
     * 提取变量或者关键字，或者时 true，false
     * @param it 字符流
     * @return
     */
    public static Token makeVarOrKeyword(PeekIterator<Character> it) {
        String tokenVal = "";
        /// 生成 Token字符串
        while (it.hasNext()) {
            Character lookahead = it.peek();
            if (AlphabetHelper.isLiteral(lookahead)) {
                tokenVal += lookahead;
            } else {
                break;
            }
            it.next();
        }
        /// 判断 Token 字符串是关键字还是变量
        if (Keywords.isKeyword(tokenVal)) {
            return new Token(TokenType.KEYWORD, tokenVal);
        }
        /// true or false
        if (tokenVal.equals("true") || tokenVal.equals("false")) {
            return new Token(TokenType.BOOLEAN, tokenVal);
        }
        /// 变量
        return new Token(TokenType.VARIABLE, tokenVal);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("type: %s, value %s", type, value);
    }
}