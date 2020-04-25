package lexer;

import java.util.regex.Pattern;

/**
 * 处理字符
 */
public class AlphabetHelper {
    /// 字母
    static Pattern ptnLetter = Pattern.compile("^[a-zA-Z]$");
    /// 数字
    static Pattern ptnNumber = Pattern.compile("^[0-9]$");
    /// 文本
    static Pattern ptnLiteral = Pattern.compile("^[_a-zA-Z0-9]$");
    /// +、-、*、/
    static Pattern ptnOperator = Pattern.compile("^[+-/\\\\*<>=!&|^%/]$");

    public static boolean isLetter(char c) {
        return ptnLetter.matcher(c+"").matches();
    }

    public static boolean isNumber(char c) {
        return ptnNumber.matcher(c+"").matches();
    }

    public static boolean isZero(char c) {
        return c == '0';
    }

    public static boolean isDot(char c) {
        return c == '.';
    }

    public static boolean isLiteral(char c) {
        return ptnLiteral.matcher(c+"").matches();
    }

    public static boolean isOperator(char c) {
        return ptnOperator.matcher(c+"").matches();
    }
}
