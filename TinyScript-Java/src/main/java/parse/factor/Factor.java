package parse.factor;

import lexer.Token;
import lexer.TokenType;
import parse.ASTNode;
import parse.ASTNodeType;
import parse.util.PeekTokenIterator;

/**
 * 表达式因子，可以是常量或者变量
 */
public abstract class Factor extends ASTNode {
    public Factor(ASTNode parent, PeekTokenIterator tokenIterator) {
        super(parent);

        Token token = tokenIterator.next();
        TokenType tokenType = token.getType();
        if (tokenType == TokenType.VARIABLE) {
            this.nodeType = ASTNodeType.VARIABLE;
        } else {
            this.nodeType = ASTNodeType.SCALAR;
        }
        this.label = token.getValue();
        this.lexeme = token;
    }
}
