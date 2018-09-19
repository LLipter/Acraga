package node;

import main.InputReader;
import exception.SyntaxException;

public class AcragaDecimalInteger {
	
	private boolean isPositive;
	private int value;
	
	public AcragaDecimalInteger(InputReader input) {
		if(input.getChCur() == '-') {
			isPositive = false;
			input.next();
		}else if(input.getChCur() == '+') {
			isPositive = true;
			input.next();
		}
		
		if(!input.isDigit())
			SyntaxException.stop(input, "invalid integer value");
		
		value = 0;
		while(input.isDigit()) {
			value *= 10;
			value += input.getChCur() - '0';
			input.next();
		}
		
		if(!isPositive)
			value *= -1;
	}
	
	public int getValue() {
		return value;
	}

}
