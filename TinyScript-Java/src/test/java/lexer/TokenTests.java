package lexer;

import common.PeekIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

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
}
