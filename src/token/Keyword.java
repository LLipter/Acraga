package token;

import type.KeywordType;
import type.TokenType;

public abstract class Keyword extends Token {

	protected KeywordType keywordType;
	
	
	public Keyword() {
		tokenType = TokenType.Keyword;
	}
}
