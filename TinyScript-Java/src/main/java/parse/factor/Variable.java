package parse.factor;

import lexer.Token;
import parse.ASTNode;
import parse.ASTNodeType;
import parse.util.PeekTokenIterator;

/**
 * 变量因子
 */
public class Variable extends Factor {
    public Variable(ASTNode parent, PeekTokenIterator tokenIterator) {
        super(parent, tokenIterator);
    }
}
