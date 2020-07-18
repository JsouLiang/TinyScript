package parse.util;

import common.PeekIterator;
import lexer.Token;
import lexer.TokenType;

import java.util.stream.Stream;

public class PeekTokenIterator extends PeekIterator<Token> {
    public PeekTokenIterator(Stream<Token> stream) {
        super(stream);
    }

    /**
     * 如果 next token 恰好 match value 那么会把当前 token 吃掉
     */
    public Token nextMatch(String value) throws ParseException {
        Token token = next();
        if (!token.getValue().equals(value)) {
            throw new ParseException(token);
        }
        return token;
    }

    public Token nextMatch(TokenType type) throws ParseException {
        Token token = next();
        if (token.getType() == type) {
            throw new ParseException(token);
        }
        return token;
    }
}
