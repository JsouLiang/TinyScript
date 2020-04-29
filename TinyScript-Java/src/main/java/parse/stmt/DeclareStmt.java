package parse.stmt;

import parse.ASTNode;
import parse.ASTNodeType;

public class DeclareStmt extends Stmt {

    public DeclareStmt(ASTNode parent) {
        super(parent, "Declare Stmt", ASTNodeType.DECLARE_STMT);
    }
}
