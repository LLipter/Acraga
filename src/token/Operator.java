package token;

import java.util.HashMap;

import type.OperatorType;
import type.TokenType;;

public abstract class Operator extends Token {
	
	protected OperatorType operatorType;
	
	public Operator() {
		tokenType = TokenType.OPERATOR;
	}
	
	public static boolean isPrioriThan(OperatorType type1, OperatorType type2) {
		HashMap<OperatorType, Integer> priorityMap = new HashMap<OperatorType, Integer>();
		priorityMap.put(OperatorType.ADD, 1);
		priorityMap.put(OperatorType.SUB, 1);
		priorityMap.put(OperatorType.MUL, 2);
		priorityMap.put(OperatorType.DIV, 2);
		return priorityMap.get(type1) > priorityMap.get(type2);
	}
}
