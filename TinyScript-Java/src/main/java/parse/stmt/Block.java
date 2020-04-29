package parse.stmt;

import parse.ASTNode;
import parse.ASTNodeType;

/**
 * 代码块语句
 */
public class Block extends Stmt {

    public Block(ASTNode parent) {
        super(parent, "Block", ASTNodeType.BLOCK);
    }
}
