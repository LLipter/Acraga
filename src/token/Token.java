package token;

public abstract class Token {

	protected TokenType tokenType;
	abstract ReturnValue run();
}
