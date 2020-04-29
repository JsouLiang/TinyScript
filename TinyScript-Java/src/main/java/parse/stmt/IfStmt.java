package parse.stmt;

import parse.ASTNode;
import parse.ASTNodeType;

public class IfStmt extends Stmt {

    public IfStmt(ASTNode parent) {
        super(parent, "if", ASTNodeType.IF_STMT);
    }
}
