package parse;

public enum ASTNodeType {
    /// 代码块
    BLOCK,
    /// 二元表达式
    BINARY_EXPR,    // 1 + 1
    /// 一元表达式
    UNARY_EXPR,     // ++i
    /// 变量
    VARIABLE,
    /// 值类型
    SCALAR,
    /// if 语句
    IF_STMT,
    /// While 语句
    WHILE_STMT,
    /// 声明语句
    DECLARE_STMT,
    /// for 语句
    FOR_STMT,
    /// 函数声明语句
    FUNCTION_DECLARE_STMT
}
