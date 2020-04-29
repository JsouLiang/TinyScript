package parse.stmt;

import parse.ASTNode;
import parse.ASTNodeType;

public class FunctionDeclareStmt extends Stmt {

    public FunctionDeclareStmt(ASTNode parent) {
        super(parent, "Function declare", ASTNodeType.FUNCTION_DECLARE_STMT);
    }
}
