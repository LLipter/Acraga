package token;

import type.SeparatorType;
import type.TokenType;

public class Separator extends Token {
	protected SeparatorType seperaterType;
	
	
	public Separator(SeparatorType sType) {
		tokenType = TokenType.Seperator;
		seperaterType = sType;
	}
}
