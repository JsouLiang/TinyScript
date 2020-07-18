package parse.factor;

import parse.ASTNode;
import parse.ASTNodeType;
import parse.util.PeekTokenIterator;

/**
 * 常量因子
 */
public class Scalar extends Factor {
    public Scalar(ASTNode parent, PeekTokenIterator tokenIterator) {
        super(parent, tokenIterator);
    }
}
