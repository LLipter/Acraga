package token;

import type.SeparatorType;
import type.TokenType;

public class Separator extends Token {

    protected SeparatorType separatorType;

    public Separator(SeparatorType sType) {
        super();
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
