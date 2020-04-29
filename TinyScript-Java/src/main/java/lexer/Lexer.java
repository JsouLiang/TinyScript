package lexer;

import common.PeekIterator;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Lexer {
    public ArrayList<Token> analyse(Stream source) {
        PeekIterator<Character> it = new PeekIterator<>(source, '\0');
        ArrayList<Token> tokens = new ArrayList<>();
        try {
            while (it.hasNext()) {
                Character currentChar = it.next();
                if (currentChar == '\0') {
                    break;
                }
                /// 空格或者换行
                if (currentChar == ' ' || currentChar == '\n') {
                    continue;
                }

                /// 注释
                if (currentChar == '/') {
                    Character nextChar = it.peek();
                    /// 单行注释
                    if (nextChar == '/') {
                        while (it.hasNext() && (currentChar = it.next()) != '\n');
                    } else if (nextChar == '*') {
                        boolean vaild = false;
                        /// 多行注释
                        while (it.hasNext()) {
                            nextChar = it.next();
                            /// 多行注释结尾
                            if (nextChar == '*' && it.peek() == '/') {
                                vaild = true;
                                it.next();
                                break;
                            }
                        }
                        if (!vaild) {
                            throw new LexicalException("注释没闭合");
                        }
                    }
                    continue;
                }

                /// {} ()
                if (currentChar == '{' || currentChar == '(' || currentChar == '}' || currentChar == ')') {
                    tokens.add(new Token(TokenType.BRACKET, currentChar + ""));
                    continue;
                }
                /// 字符串 Token
                if (currentChar == '"' || currentChar == '\'') {
                    it.putBack();
                    tokens.add(Token.makeString(it));
                    continue;
                }
                /// 数字提取
                if (AlphabetHelper.isNumber(currentChar)) {
                    it.putBack();
                    tokens.add(Token.makeNumber(it));
                    continue;
                }

                /// 关键字或变量提取
                if (AlphabetHelper.isLiteral(currentChar)) {
                    it.putBack();
                    tokens.add(Token.makeVarOrKeyword(it));
                    continue;
                }

                /// +5, -5, .5
                Character nextChar = it.peek();
                if ((currentChar == '+' || currentChar == '-' || AlphabetHelper.isDot(currentChar)) && AlphabetHelper.isNumber(nextChar)) {
                    Token lastToken = tokens.size() == 0 ? null : tokens.get(tokens.size() - 1);
                    /// 如果前一个 token 是操作符，或者不是数字，或者为空，那么 +,-,. 应该构成数字
                    /// -100, 100 * -10, 100 * .10
                    if (lastToken == null || !lastToken.isNumber() || lastToken.isOperator()) {
                        it.putBack();
                        tokens.add(Token.makeNumber(it));
                        continue;
                    }
                }
                /// + - * / 等操作符
                if (AlphabetHelper.isOperator(currentChar)) {
                    it.putBack();
                    tokens.add(Token.makeOperator(it));
                }
            }
        } catch (LexicalException exception) {
            System.out.println(exception);
        }

        return tokens;
    }
}