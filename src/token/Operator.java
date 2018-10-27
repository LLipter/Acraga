package token;

import type.OperatorType;
import type.TokenType;

import java.util.HashMap;

;

public abstract class Operator extends ExpressionToken {

    protected OperatorType operatorType;

    private static HashMap<OperatorType, Integer> priorityMap = new HashMap<OperatorType, Integer>();

    static{
        priorityMap.put(OperatorType.ASSIGN,1);
        priorityMap.put(OperatorType.EQUAL,2);
        priorityMap.put(OperatorType.NOTEQUAL,2);
        priorityMap.put(OperatorType.GREATERTHAN,3);
        priorityMap.put(OperatorType.LESSTHAN,3);
        priorityMap.put(OperatorType.GREATERTHANOREQUAL,3);
        priorityMap.put(OperatorType.LESSTHANOREQUAL,3);
        priorityMap.put(OperatorType.ADD,4);
        priorityMap.put(OperatorType.SUB,4);
        priorityMap.put(OperatorType.MUL,5);
        priorityMap.put(OperatorType.DIV,5);
        priorityMap.put(OperatorType.MOD,5);



        priorityMap.put(OperatorType.NEGATIVESIGN,1);
        priorityMap.put(OperatorType.NOT,2);
        priorityMap.put(OperatorType.BITWISENEGATE,2);
        priorityMap.put(OperatorType.MOD,3);
        priorityMap.put(OperatorType.DIV,3);
        priorityMap.put(OperatorType.MUL,3);
        priorityMap.put(OperatorType.ADD,4);
        priorityMap.put(OperatorType.SUB,4);
        priorityMap.put(OperatorType.LEFTSHIFTING,5);
        priorityMap.put(OperatorType.RIGHTSHIFTING,5);
        priorityMap.put(OperatorType.LESSTHAN,6);
        priorityMap.put(OperatorType.LESSTHANOREQUAL,6);
        priorityMap.put(OperatorType.GREATERTHAN,6);
        priorityMap.put(OperatorType.GREATERTHANOREQUAL,6);
        priorityMap.put(OperatorType.EQUAL,7);
        priorityMap.put(OperatorType.NOTEQUAL,7);
        priorityMap.put(OperatorType.BITWISEAND,8);
        priorityMap.put(OperatorType.BITWISEXOR,9);
        priorityMap.put(OperatorType.BITWISEOR,10);
        priorityMap.put(OperatorType.LOGICALAND,11);
        priorityMap.put(OperatorType.LOGICALOR,12);

    }

    public Operator() {
        tokenType = TokenType.OPERATOR;
    }

    public static boolean isPrioriThan(OperatorType type1, OperatorType type2) {
        return priorityMap.get(type1) < priorityMap.get(type2);
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }
}
