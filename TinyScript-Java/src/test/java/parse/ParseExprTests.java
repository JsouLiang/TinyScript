package parse;

import jdk.jshell.spi.ExecutionControl;
import lexer.Lexer;
import org.junit.jupiter.api.Test;
import parse.util.ParseException;
import parse.util.ParserUtils;
import parse.util.PeekTokenIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParseExprTests {
    @Test
    public void simple() throws ParseException, ExecutionControl.NotImplementedException {
        var expr = createExpr("1+1+1");
        assertEquals("1 1 1 + +", ParserUtils.toPostfixExpression(expr));
    }

    private ASTNode createExpr(String src) throws ParseException {
        var lexer = new Lexer();
        var tokens = lexer.analyse(src.chars().mapToObj(x -> (char)x));
        var tokenIt = new PeekTokenIterator(tokens.stream());
        return Expr.parse(tokenIt);
    }
}
