package token.exprtoken.operator;

import token.exprtoken.ExpressionToken;
import type.OperatorType;
import type.TokenType;

import java.util.HashMap;

public abstract class Operator extends ExpressionToken {

    private static HashMap<OperatorType, Integer> priorityMap = new HashMap<OperatorType, Integer>();

    static {
        priorityMap.put(OperatorType.CASTING, 2);
        priorityMap.put(OperatorType.NEGATIVESIGN, 2);
        priorityMap.put(OperatorType.POSITIVESIGN, 2);
        priorityMap.put(OperatorType.LOGICALNOT, 2);
        priorityMap.put(OperatorType.BITWISENEGATE, 2);
        priorityMap.put(OperatorType.SELFINCREMENT, 2);
        priorityMap.put(OperatorType.SELFDECREMENT, 2);
        priorityMap.put(OperatorType.MOD, 3);
        priorityMap.put(OperatorType.DIV, 3);
        priorityMap.put(OperatorType.MUL, 3);
        priorityMap.put(OperatorType.ADD, 4);
        priorityMap.put(OperatorType.SUB, 4);
        priorityMap.put(OperatorType.LEFTSHIFTING, 5);
        priorityMap.put(OperatorType.RIGHTSHIFTING, 5);
        priorityMap.put(OperatorType.LESSTHAN, 6);
        priorityMap.put(OperatorType.LESSTHANOREQUAL, 6);
        priorityMap.put(OperatorType.GREATERTHAN, 6);
        priorityMap.put(OperatorType.GREATERTHANOREQUAL, 6);
        priorityMap.put(OperatorType.EQUAL, 7);
        priorityMap.put(OperatorType.NOTEQUAL, 7);
        priorityMap.put(OperatorType.BITWISEAND, 8);
        priorityMap.put(OperatorType.BITWISEXOR, 9);
        priorityMap.put(OperatorType.BITWISEOR, 10);
        priorityMap.put(OperatorType.LOGICALAND, 11);
        priorityMap.put(OperatorType.LOGICALOR, 12);
        priorityMap.put(OperatorType.ASSIGN, 14);
        priorityMap.put(OperatorType.ADDASSIGN, 14);
        priorityMap.put(OperatorType.SUBASSIGN, 14);
        priorityMap.put(OperatorType.MULASSIGN, 14);
        priorityMap.put(OperatorType.DIVASSIGN, 14);
        priorityMap.put(OperatorType.MODASSIGN, 14);
        priorityMap.put(OperatorType.BITWISEANDASSIGN, 14);
        priorityMap.put(OperatorType.BITWISEORASSIGN, 14);
        priorityMap.put(OperatorType.BITWISEXORASSIGN, 14);
        priorityMap.put(OperatorType.LEFTSHIFTINGASSIGN, 14);
        priorityMap.put(OperatorType.RIGHTSHIFTINGASSIGN, 14);
    }

    protected OperatorType operatorType;

    public Operator() {
        tokenType = TokenType.OPERATOR;
    }

    public static boolean isPrioriThan(Operator op1, Operator op2) {
        return priorityMap.get(op1.getOperatorType()) < priorityMap.get(op2.getOperatorType());
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }
}
