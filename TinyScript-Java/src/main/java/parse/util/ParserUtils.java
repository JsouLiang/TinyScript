package parse.util;

import jdk.jshell.spi.ExecutionControl;
import parse.ASTNode;

public class ParserUtils {
    public static String toPostfixExpression(ASTNode node) throws ExecutionControl.NotImplementedException {
        String leftStr = "";
        String rightStr = "";
        switch (node.getType()) {
            case BINARY_EXPR:
                leftStr = toPostfixExpression(node.getChild(0));
                rightStr = toPostfixExpression(node.getChild(1));
                return leftStr + " " + rightStr + node.getLexeme().getValue();
            case VARIABLE:
            case SCALAR:
                return node.getLexeme().getValue();

        }
        throw new ExecutionControl.NotImplementedException("no impl");
    }
}
