package parse.factor;

import parse.ASTNode;
import parse.ASTNodeType;

/**
 * 变量因子
 */
public class Variable extends Factor {
    public Variable(ASTNode parent, String label, ASTNodeType nodeType) {
        super(parent, "", ASTNodeType.VARIABLE);
    }
}
