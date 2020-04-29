package parse.factor;

import parse.ASTNode;
import parse.ASTNodeType;

/**
 * 常量因子
 */
public class Scalar extends Factor {
    public Scalar(ASTNode parent, String label, ASTNodeType nodeType) {
        super(parent, "", ASTNodeType.SCALAR);
    }
}
