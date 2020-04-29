package parse.stmt;

import parse.ASTNode;
import parse.ASTNodeType;

public class ForStmt extends Stmt {
    public ForStmt(ASTNode parent) {
        super(parent, "for", ASTNodeType.FOR_STMT);
    }
}
