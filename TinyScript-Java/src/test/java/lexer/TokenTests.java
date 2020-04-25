package lexer;

import common.PeekIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTests {
    void assertToken(Token token, String exceptTokenValue, TokenType exceptTokenType) {
        assertEquals(exceptTokenType, token.getType());
        assertEquals(exceptTokenValue, token.getValue());
    }
    @Test
    public void test_varOrKeyword() {
        var it1 = new PeekIterator<Character>("if abc".chars().mapToObj(c -> (char)c));
        var it2 = new PeekIterator<Character>("true abc".chars().mapToObj(c -> (char)c));
        var token1 = Token.makeVarOrKeyword(it1);
        assertToken(token1, "if", TokenType.KEYWORD);

        var token2 = Token.makeVarOrKeyword(it2);
        assertToken(token2, "true", TokenType.BOOLEAN);

        /// 略过空格
        it1.next();
        var token3 = Token.makeVarOrKeyword(it1);
        assertToken(token3, "abc", TokenType.VARIABLE);

        /// 略过空格
        it2.next();
        var token4 = Token.makeVarOrKeyword(it2);
        assertToken(token4, "abc", TokenType.VARIABLE);
    }

    @Test
    public void test_makeString() throws LexicalException {
        String[] tests = {
                "\"123\"",
                "'123'",
        };
        for (String test : tests) {
            PeekIterator<Character> it = new PeekIterator<>(test.chars().mapToObj(c -> (char)c));
            Token token = Token.makeString(it);
            assertToken(token, test, TokenType.STRING);
        }
    }

    @Test
    public void test_makeOperator() throws LexicalException {
        var it = new PeekIterator<Character>("+=".chars().mapToObj(c -> (char)c));
        Token token = Token.makeOperator(it);
        assertToken(token, "+=", TokenType.OPERATOR);

        it = new PeekIterator<Character>("+abc".chars().mapToObj(c -> (char)c));
        token = Token.makeOperator(it);
        assertEquals('a', it.next());
        assertToken(token, "+", TokenType.OPERATOR);

        it = new PeekIterator<Character>("+ xxx".chars().mapToObj(c -> (char)c));
        token = Token.makeOperator(it);
        assertEquals(' ', it.next());
        assertToken(token, "+", TokenType.OPERATOR);

        it = new PeekIterator<Character>("++mm".chars().mapToObj(c -> (char)c));
        token = Token.makeOperator(it);
        assertEquals('m', it.next());
        assertToken(token, "++", TokenType.OPERATOR);

        it = new PeekIterator<Character>("/=g".chars().mapToObj(c -> (char)c));
        token = Token.makeOperator(it);
        assertEquals('g', it.next());
        assertToken(token, "/=", TokenType.OPERATOR);

        it = new PeekIterator<Character>("&=888".chars().mapToObj(c -> (char)c));
        token = Token.makeOperator(it);
        assertEquals('8', it.next());
        assertToken(token, "&=", TokenType.OPERATOR);

        it = new PeekIterator<Character>("&777".chars().mapToObj(c -> (char)c));
        token = Token.makeOperator(it);
        assertEquals('7', it.next());
        assertToken(token, "&", TokenType.OPERATOR);

        it = new PeekIterator<Character>("||xx".chars().mapToObj(c -> (char)c));
        token = Token.makeOperator(it);
        assertEquals('x', it.next());
        assertToken(token, "||", TokenType.OPERATOR);

        it = new PeekIterator<Character>("^=1".chars().mapToObj(c -> (char)c));
        token = Token.makeOperator(it);
        assertEquals('1', it.next());
        assertToken(token, "^=", TokenType.OPERATOR);

        it = new PeekIterator<Character>("%7".chars().mapToObj(c -> (char)c));
        token = Token.makeOperator(it);
        assertEquals('7', it.next());
        assertToken(token, "%", TokenType.OPERATOR);
    }

    @Test
    public void test_makeNumber() throws LexicalException {

    }
}
