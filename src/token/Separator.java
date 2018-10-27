package token;

import type.SeparatorType;
import type.TokenType;

public class Separator extends Token {
    protected SeparatorType separatorType;


    public Separator(SeparatorType sType) {
        tokenType = TokenType.SEPARATOR;
        separatorType = sType;
    }

    @Override
    public String toString() {
        return String.format("<Separator,%s>", separatorType);
    }

    public SeparatorType getSeparatorType() {
        return separatorType;
    }


}
