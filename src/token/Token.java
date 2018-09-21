package token;

import type.TokenType;

public abstract class Token {

	protected TokenType tokenType;
	public abstract ReturnValue run();
}
