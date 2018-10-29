package token;

import type.TokenType;
import type.SeparatorType;

public class Separator extends Token {

    protected SeparatorType separatorType;

    public Separator(SeparatorType sType) {
        separatorType = sType;
        tokenType = TokenType.SEPARATOR;
    }

    @Override
    public String toString() {
        return String.format("<Separator,%s>", separatorType);
    }

    public SeparatorType getSeparatorType() {
        return separatorType;
    }


}
