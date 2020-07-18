package parse.util;

import parse.ASTNode;

/**
 * HOF: High Order Function
 */
@FunctionalInterface
public interface ExprHOF {
    ASTNode hoc() throws  ParseException;
}
