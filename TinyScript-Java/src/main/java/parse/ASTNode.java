package parse;

import lexer.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象语法树节点
 */
public abstract class ASTNode {
    protected List<ASTNode> children;
    protected ASTNode parent;
    /**
     * 词法单元
     */
    protected Token lexeme;
    /**
     * 备注，描述信息
     */
    protected String label;

    protected ASTNodeType nodeType;

    public ASTNode(ASTNode parent) {
        this.parent = parent;
    }

    public ASTNode(ASTNode parent, String label, ASTNodeType nodeType) {
        this.parent = parent;
        this.label = label;
        this.nodeType = nodeType;
    }

    public ASTNode getChild(int index) {
        if (index > children.size()) {
            return null;
        }
        return children.get(index);
    }

    public void addChild(ASTNode node) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(node);
    }

    public Token getLexeme() {
        return lexeme;
    }

    public void setChildren(List<ASTNode> children) {
        this.children = children;
    }
}
