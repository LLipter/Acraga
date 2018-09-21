package token;

import type.KeywordType;
import type.TokenType;

public class Identifier extends Token {
	
	private String id;
	
	public Identifier() {
		tokenType = TokenType.Identifier;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
