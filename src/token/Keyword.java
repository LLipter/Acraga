package token;

import type.KeywordType;
import type.TokenType;

public class Keyword extends Token {

	protected KeywordType keywordType;
	
	
	public Keyword(KeywordType kType) {
		tokenType = TokenType.Keyword;
		keywordType = kType;
	}
	
	
	
}
