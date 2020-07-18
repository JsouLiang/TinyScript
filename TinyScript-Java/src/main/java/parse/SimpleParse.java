package parse;

import lexer.Token;
import parse.factor.Scalar;
import parse.util.ParseException;
import parse.util.PeekTokenIterator;

import java.beans.Expression;

public class SimpleParse {
    /**
     * Expr -> digit + Expr | digit
     * digit: 0 ~ 9
     * @param it
     * @return
     */
    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        Expr expression = new Expr(null);
        Scalar digit = new Scalar(expression, it);
        /// 处理终结符
        if (!it.hasNext()) {
            return digit;
        }
        expression.setLexeme(it.peek());
        it.nextMatch("+");

        expression.setLabel("+");
        expression.addChild(digit);
        expression.setType(ASTNodeType.BINARY_EXPR);
        /// expression matched digit + Expr
        /// 非终结符
        ASTNode expr = parse(it);
        expression.addChild(expr);
        return expression;
    }
}
