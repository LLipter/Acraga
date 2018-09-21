package token;

import java.util.HashMap;;

public abstract class Operator extends Token {
	
	protected OperatorType operatorType;
	
	public Operator() {
		tokenType = TokenType.Operator;
	}
	
	public static boolean isPrioriThan(OperatorType type1, OperatorType type2) {
		HashMap<OperatorType, Integer> priorityMap = new HashMap<OperatorType, Integer>();
		priorityMap.put(OperatorType.Add, 1);
		priorityMap.put(OperatorType.Subtract, 1);
		priorityMap.put(OperatorType.Multiply, 2);
		priorityMap.put(OperatorType.Divide, 2);
		return priorityMap.get(type1) > priorityMap.get(type2);
	}
}
