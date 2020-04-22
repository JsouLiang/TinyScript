package common;

import common.PeekIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeekIteratorTests {
    @Test
    public void testPeek() {
        String source = "abcdefg";
        PeekIterator<Character> it = new PeekIterator<Character>(source.chars().mapToObj(c->(char)c));
        assertEquals('a', it.next());
        assertEquals('b', it.next());
        it.next();
        it.next();
        assertEquals('e', it.next());
        assertEquals('f', it.next());
        assertEquals('g', it.next());
    }

    @Test
    public void test_lookhead2() {
        String source = "abcdefg";
        PeekIterator<Character> it = new PeekIterator<Character>(source.chars().mapToObj(c->(char)c));
        assertEquals('a', it.next());
        assertEquals('b', it.next());
        it.putBack();
        it.putBack();
        assertEquals('a', it.next());
    }

    @Test
    public void test_endToken() {
        String source = "abcdefg";
        PeekIterator<Character> it = new PeekIterator<Character>(source.chars().mapToObj(c->(char)c));
        int index = 0;
        while (it.hasNext()) {
            if (index == 7) {
                assertEquals((char)0, it.next());
            } else {
                assertEquals(source.charAt(index++), it.next());
            }
        }
    }
}