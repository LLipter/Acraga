package token;

public abstract class Operator extends Token {
	
	protected OperatorType operatorType;
	
	public Operator() {
		tokenType = TokenType.Operator;
	}
}
