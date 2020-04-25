package lexer;

import common.PeekIterator;

public class Token {
    TokenType type;
    String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    /// 是否是变量
    public boolean isVariable() {
        return type == TokenType.VARIABLE;
    }

    /// 是否是值
    public boolean isScalar() {
        return type == TokenType.INTEGER || type == TokenType.FLOAT || type == TokenType.BOOLEAN || type == TokenType.STRING;
    }

    /**
     * 提取变量或者关键字，或者时 true，false
     *
     * @param it 字符流
     * @return
     */
    public static Token makeVarOrKeyword(PeekIterator<Character> it) {
        String tokenVal = "";
        /// 生成 Token字符串
        while (it.hasNext()) {
            /// 先取出当前字符查看，如果是 literal 则处理，否则不作处理
            Character lookahead = it.peek();
            if (AlphabetHelper.isLiteral(lookahead)) {
                tokenVal += lookahead;
            } else {
                break;
            }
            it.next();
        }
        /// 判断 Token 字符串是关键字还是变量
        if (Keywords.isKeyword(tokenVal)) {
            return new Token(TokenType.KEYWORD, tokenVal);
        }
        /// true or false
        if (tokenVal.equals("true") || tokenVal.equals("false")) {
            return new Token(TokenType.BOOLEAN, tokenVal);
        }
        /// 变量
        return new Token(TokenType.VARIABLE, tokenVal);
    }

    /**
     * 使用状态机，提取字符串的值
     *
     * @param it 字符串流，以" 开头或者' 开头
     * @return state 初始为 0
     * 0 -> 1  ：状态 0 接受了一个 " 字符
     * 0 -> 2  ：状态 0 接受一个 ' 字符
     * <p>
     * 1 -> 1 : 状态 1 接受一个非“ 的字符
     * 1 -> str: 状态 1 接受一个 " 字符
     * <p>
     * 2 -> 2 : 状态 2 接受一个非' 的字符
     * 2 -> str : 状态 1 接受一个 ' 字符
     */
    public static Token makeString(PeekIterator<Character> it) throws LexicalException {
        StringBuilder tokenValue = new StringBuilder();
        int state = 0;
        while (it.hasNext()) {
            char currentChar = it.next();
            switch (state) {
                case 0:
                    if (currentChar == '"') {
                        state = 1;
                    } else {
                        state = 2;
                    }
                    tokenValue.append(currentChar);
                    break;
                case 1:
                    if (currentChar == '"') {
                        tokenValue.append(currentChar);
                        return new Token(TokenType.STRING, tokenValue.toString());
                    } else {
                        tokenValue.append(currentChar);
                    }
                    break;
                case 2:
                    if (currentChar == '\'') {
                        tokenValue.append(currentChar);
                        return new Token(TokenType.STRING, tokenValue.toString());
                    } else {
                        tokenValue.append(currentChar);
                    }
            }
        }
        throw new LexicalException("Exception Error");
    }

    /**
     * 使用状态机提取操作符
     * @param it 字符流
     */
    public static Token makeOperator(PeekIterator<Character> it) throws LexicalException {
        StringBuilder operator = new StringBuilder();
        int state = 0;
        while (it.hasNext()) {
            Character currentChar = it.next();
            operator.append(currentChar);
            switch (state) {
                case 0: {
                    if (currentChar == ',' || currentChar == ';') {
                        return new Token(TokenType.OPERATOR, operator.toString());
                    } else if (currentChar == '+') {
                        state = 1;
                    } else if (currentChar == '-') {
                        state = 2;
                    } else if (currentChar == '*') {
                        state = 3;
                    } else if (currentChar == '/') {
                        state = 4;
                    } else if (currentChar == '>') {
                        state = 5;
                    } else if (currentChar == '<') {
                        state = 6;
                    } else if (currentChar == '=') {
                        state = 7;
                    } else if (currentChar == '!') {
                        state = 8;
                    } else if (currentChar == '&') {
                        state = 9;
                    } else if (currentChar == '|') {
                        state = 10;
                    } else if (currentChar == '^') {
                        state = 11;
                    } else if (currentChar == '%') {
                        state = 12;
                    }
                    break;
                }
                case 1: {
                    if (!(currentChar == '+' || currentChar == '=')) {
                        operator.deleteCharAt(operator.length() - 1);
                        it.putBack();
                    }
                    /// ++  += +
                    return new Token(TokenType.OPERATOR, operator.toString());
                }
                case 2: {
                    if (!(currentChar == '-' || currentChar == '=')) {
                        operator.deleteCharAt(operator.length() - 1);
                        it.putBack();
                    }
                    /// --  -= -
                    return new Token(TokenType.OPERATOR, operator.toString());
                }
                case 3:
                case 4:
                case 7:
                case 8:
                case 11:
                case 12: {
                    if (!(currentChar == '=')) {
                        operator.deleteCharAt(operator.length() - 1);
                        it.putBack();
                    }
                    /// * *=  / /= == != ^ ^= % %=
                    return new Token(TokenType.OPERATOR, operator.toString());
                }
                case 5: {
                    if (!(currentChar == '>' || currentChar == '=')) {
                        operator.deleteCharAt(operator.length() - 1);
                        it.putBack();
                    }
                    /// >> >= >
                    return new Token(TokenType.OPERATOR, operator.toString());
                }
                case 6: {
                    if (!(currentChar == '<' || currentChar == '=')) {
                        operator.deleteCharAt(operator.length() - 1);
                        it.putBack();
                    }
                    /// << <= <
                    return new Token(TokenType.OPERATOR, operator.toString());
                }/// ++  += +
                case 9: {
                    if (!(currentChar == '&' || currentChar == '=')) {
                        operator.deleteCharAt(operator.length() - 1);
                        it.putBack();
                    }
                    /// && &= &
                    return new Token(TokenType.OPERATOR, operator.toString());
                }
                case 10: {
                    if (!(currentChar == '|' || currentChar == '=')) {
                        operator.deleteCharAt(operator.length() - 1);
                        it.putBack();
                    }
                    /// |= | ||
                    return new Token(TokenType.OPERATOR, operator.toString());
                }
            }
        }
        throw new LexicalException("Exception");
    }

    public static Token makeNumber(PeekIterator<Character> it) throws LexicalException {
        StringBuilder number = new StringBuilder();
        int state = 0;
        while (it.hasNext()) {
            Character currentChar = it.next();
            number.append(currentChar);
            switch (state) {
                case 0: {
                    if (AlphabetHelper.isNumber(currentChar)) {
                        if (AlphabetHelper.isZero(currentChar)) {
                            state = 1;
                        } else {
                            state = 2;
                        }
                    } else if (currentChar == '-' || currentChar == '+') {
                        state = 3;
                    } else if (AlphabetHelper.isDot(currentChar)) {
                        state = 5;
                    }
                    break;
                }
                case 1: {
                    if (AlphabetHelper.isNumber(currentChar)) {
                        if (AlphabetHelper.isZero(currentChar)) {
                            state = 1;
                        } else {
                            state = 2;
                        }
                    } else if (AlphabetHelper.isDot(currentChar)) {
                        state = 4;
                    } else {
                        it.putBack();
                        number.deleteCharAt(number.length() - 1);
                        return new Token(TokenType.INTEGER, number.toString());
                    }
                    break;
                }
                case 2: {
                    if (AlphabetHelper.isNumber(currentChar)) {
                        state = 2;
                    } else if (AlphabetHelper.isDot(currentChar)) {
                        state = 4;
                    } else {
                        it.putBack();
                        number.deleteCharAt(number.length() - 1);
                        return new Token(TokenType.INTEGER, number.toString());
                    }
                    break;
                }
                case 3: {
                    if (AlphabetHelper.isNumber(currentChar)) {
                        state = 2;
                    } else if (AlphabetHelper.isDot(currentChar)) {
                        state = 5;
                    } else {
                        throw new LexicalException("Exception");
                    }
                    break;
                }
                case 4:
                case 6: {
                    if (AlphabetHelper.isNumber(currentChar)) {
                        state = 6;
                    } else if (AlphabetHelper.isDot(currentChar)) {
                        throw  new LexicalException("Exception");
                    } else {
                        it.putBack();
                        number.deleteCharAt(number.length() - 1);
                        return new Token(TokenType.FLOAT, number.toString());
                    }
                    break;
                }
                case 5: {
                    if (AlphabetHelper.isNumber(currentChar)) {
                        state = 6;
                    } else {
                        throw  new LexicalException("Exception");
                    }
                    break;
                }
            }
        }
        throw  new LexicalException("Exception");
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("type: %s, value %s", type, value);
    }
}