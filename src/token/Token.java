package token;

public abstract class Token {

	protected TokenType tokenType;
	public abstract ReturnValue run();
}
