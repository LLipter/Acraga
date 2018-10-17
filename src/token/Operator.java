package token;

import type.OperatorType;
import type.TokenType;

import java.util.HashMap;

;

public abstract class Operator extends ExpressionToken {

    protected OperatorType operatorType;
    private static HashMap<OperatorType, Integer> priorityMap = new HashMap<OperatorType, Integer>();

    static{
        priorityMap.put(OperatorType.ADD, 1);
        priorityMap.put(OperatorType.SUB, 1);
        priorityMap.put(OperatorType.MUL, 2);
        priorityMap.put(OperatorType.DIV, 2);
    }

    public Operator() {
        tokenType = TokenType.OPERATOR;
    }

    public static boolean isPrioriThan(OperatorType type1, OperatorType type2) {
        return priorityMap.get(type1) > priorityMap.get(type2);
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }
}
