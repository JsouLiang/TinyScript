package parse.stmt;

import parse.ASTNode;
import parse.ASTNodeType;

/**
 * 语句
 */
public abstract class Stmt extends ASTNode {
    public Stmt(ASTNode parent, String label, ASTNodeType nodeType) {
        super(parent, label, nodeType);
    }
}
