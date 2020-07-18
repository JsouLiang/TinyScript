package parse;

import lexer.Lexer;
import org.junit.jupiter.api.Test;
import parse.util.ParseException;
import parse.util.PeekTokenIterator;

public class SimpleParseTests {
    @Test
    public void test() throws ParseException {
        var source = "1 + 2 + 3 + 4".chars().mapToObj(c -> (char)c);
        var lexer = new Lexer();
        var it = new PeekTokenIterator(lexer.analyse(source).stream());
        var expr = SimpleParse.parse(it);
        System.out.println(expr);
    }
}
