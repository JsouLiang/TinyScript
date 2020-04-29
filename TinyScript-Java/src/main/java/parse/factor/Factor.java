package parse.factor;

import parse.ASTNode;
import parse.ASTNodeType;

/**
 * 表达式因子，可以是常量或者变量
 */
public abstract class Factor extends ASTNode {
    public Factor(ASTNode parent, String label, ASTNodeType nodeType) {
        super(parent, label, nodeType);
    }
}
