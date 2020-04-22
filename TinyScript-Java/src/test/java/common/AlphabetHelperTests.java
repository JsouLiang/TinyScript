package common;

import lexer.AlphabetHelper;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class AlphabetHelperTests {
    @Test
    public void test() {
        assertEquals(true, AlphabetHelper.isLetter('a'));
        assertEquals(true, AlphabetHelper.isOperator('+'));
        assertEquals(true, AlphabetHelper.isOperator('-'));
        assertEquals(true, AlphabetHelper.isOperator('*'));
        assertEquals(true, AlphabetHelper.isOperator('/'));
        assertEquals(true, AlphabetHelper.isOperator('<'));
        assertEquals(true, AlphabetHelper.isOperator('>'));
        assertEquals(true, AlphabetHelper.isOperator('&'));
        assertEquals(true, AlphabetHelper.isOperator('!'));

    }
}

