package token;

import type.KeywordType;
import type.TokenType;

public class Keyword extends Token {

    protected KeywordType keywordType;


    public Keyword(KeywordType kType) {
        tokenType = TokenType.KEYWORD;
        keywordType = kType;
    }

    @Override
    public String toString() {
        return String.format("<Keyword,%s>", keywordType);
    }

    public KeywordType getKeywordType() {
        return keywordType;
    }


}
