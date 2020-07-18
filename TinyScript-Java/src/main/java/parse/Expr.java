package parse;

import lexer.Token;
import parse.factor.Scalar;
import parse.factor.Variable;
import parse.util.ExprHOF;
import parse.util.ParseException;
import parse.util.PeekTokenIterator;
import parse.util.PriorityTable;

/**
 * 表达式
 */
public class Expr extends ASTNode {

    static private PriorityTable table = new PriorityTable();

    public Expr(ASTNode parent) {
        super(parent);
    }

    public Expr(ASTNode parent, ASTNodeType type, Token lexeme) {
        super(parent);
        this.nodeType = type;
        this.lexeme = lexeme;
        this.label = lexeme.getValue();
    }

    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        return E(null, 0, it);
    }

    /**
     * 文法：
     * 左递归：A -> A op B|B
     * => B op B op B op B... 递归的是 op B, 结束的点是 A -> B
     * 转成右递归：
     * 1. 提取递归项：op B, 生成新的文法 A': A' -> op B A'
     * 2. 新文法结束点是空集： A' -> Ø
     * 3. A -> B A'
     *    A' -> op B A' | Ø
     *
     * 右递归：
     * A -> BA'
     * A'-> op B | Ø
     *
     * 左递归：E(k) -> E(k) op(k) E(k+1) | E(k+1)
     * 右递归：E(k) -> E(K + 1) E'(K)
     *        E'(K) -> op(k) E(K + 1) E'(K) | Ø
     *
     * ===>
     * 转成右递归后的表达式
     * var e = new Expr(), e.left = E(k+1) e.op = op(k) e.right = E(k+1)E'(k)
     *
     * E(k) -> E(K + 1) op(k) E(K + 1) E'(K) | Ø
     * ===>
     * expr: expr.left = E(K + 1), expr.right = E(K + 1) E'(K)  expr.op = E'(K).op  expr.type = E'(K).type
     *           E
     *      /    |    \
     *  E(k+1)  op(k)  E(k+1)E'(k)
     *
     * 最高优先级：
     *  E(t) -> F E'(k) | U E'(k)
     *  E'(t) -> op(t) E(t) E'(t) | Ø
     */
    public static ASTNode E(ASTNode parent, int k, PeekTokenIterator it) throws ParseException {
        // var a = E(k+1)
        // var b = E'(k)
        // combine(a, b);  // E(k) -> E(k+1)E'(k)
        if (k < table.size() - 1) {
            return combine(parent, () -> E( parent, k + 1, it), () -> E_(parent, k, it), it);
        }
        // 最高优先级
        return race(
                () -> combine(parent, () -> U(parent, it), () -> E_(parent, k, it), it),
                () -> combine(parent, () -> F(parent, it), () -> E_(parent, k, it), it),
                it
        );

    }

    /**
     * E(k) -> E(k) op(k) E(k+1) | E(k+1)
     *
     * E(k) -> E(k+1)E'(k)
     * E'(k) -> op(k)E(k+1)E'(k) | Ø
     * 消除左递归中的 E'函数
     */
    public static ASTNode E_(ASTNode parent, int k, PeekTokenIterator it) throws ParseException {
        var token = it.peek();
        var value = token.getValue();
        // E'(k) -> op E(k+1) E'(k) | Ø
        if (table.get(k).indexOf(value) != -1) {
            // 如果 token 流中第一个是 op
            var expr = new Expr(parent, ASTNodeType.BINARY_EXPR, it.nextMatch(value));
            expr.addChild(
                    combine(parent,
                            () -> E(parent, k + 1, it),     // E(k+1)
                            () -> E_(parent, k, it), it)       // E'(k)
            );
        }
        return null;
    }

    /**
     * 一元表达式
     * @param parent
     * @param it
     * @return
     */
    private static ASTNode U(ASTNode parent, PeekTokenIterator it) throws ParseException {
        // 看一下下一个 token
        var token = it.peek();
        var value = token.getValue();
        ASTNode exp;
        // 括号
        if (value.equals("(")) {
            // 将 ( 吃掉
            it.nextMatch("(");
            // (E(0))
            exp = E(parent, 0, it);
            it.nextMatch(")");
            return exp;
        } else if (value.equals("++") || value.equals("--") || value.equals("!")) {
            var currentToken = it.peek();
            // 将++，--，！吃掉
            it.nextMatch(currentToken.getValue());
            Expr unaryExpr = new Expr(parent, ASTNodeType.UNARY_EXPR, currentToken);
            // ++,--,! 后面可以是个表达式
            unaryExpr.addChild(E(unaryExpr, 0, it));
            return unaryExpr;
        }
        return null;
    }

    private static ASTNode F(ASTNode parent, PeekTokenIterator it) {
        var token = it.peek();
        if (token.isVariable()) {
            return new Variable(parent, it);
        }
        return new Scalar(parent, it);
    }

    /**
     * 当 combin(E(k+1), E'(k)) 的时候：
     * 1. 如果 E(k+1) 为 null 直接返回 E'(k)
     * 2. 如果 E'(k) 为 null，那么吧 E(k+1) 返回
     * 3. 如果走到这一步，说明 E(k+1) 和 E'(k) 都不为 null，
     * 创建 Exp，此时 Exp.left = E(k+1),
     * Exp.right = E'(k)
     * 其中E'(k) = op(k) E(k+1) E'(k)
     * => Exp.right = op(k)
     * Exp.op = op(k)
     * 
     */
    private static ASTNode combine(ASTNode parent, ExprHOF leftFunc, ExprHOF rightFunc, PeekTokenIterator it) throws ParseException {
        /// A -> BA'
        /// 如果 B 为空，返回 A'
        var left = leftFunc.hoc();
        if (left == null) {
            return it.hasNext() ? rightFunc.hoc() : null;
        }
        var right = it.hasNext() ? rightFunc.hoc() : null;
        if (right == null) {
            return left;
        }
        Expr expr = new Expr(parent, ASTNodeType.BINARY_EXPR, right.lexeme);
        expr.addChild(left);
        /// E' -> op E' E
        /// E' => children:[op, E', E]
        expr.addChild(right.getChild(0));
        return expr;
    }

    private static ASTNode race(ExprHOF leftFunc, ExprHOF rightFunc, PeekTokenIterator it) throws ParseException {
        if (!it.hasNext()) {
            return null;
        }
        var a = leftFunc.hoc();
        if (a != null) {
            return a;
        }
        return rightFunc.hoc();
    }
}
