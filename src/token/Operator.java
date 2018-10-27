package token;

import type.OperatorType;
import type.TokenType;

import java.util.HashMap;

public abstract class Operator extends ExpressionToken {

    protected OperatorType operatorType;

    private static HashMap<OperatorType, Integer> outStackPriorityMap = new HashMap<OperatorType, Integer>();
    private static HashMap<OperatorType, Integer> inStackPriorityMap = new HashMap<OperatorType, Integer>();

    static{
        outStackPriorityMap.put(OperatorType.LEFTPARENTHESES,1);
        outStackPriorityMap.put(OperatorType.LEFTBRACKET,1);
        outStackPriorityMap.put(OperatorType.NEGATIVESIGN,2);
        outStackPriorityMap.put(OperatorType.NOT,2);
        outStackPriorityMap.put(OperatorType.BITWISENEGATE,2);
        outStackPriorityMap.put(OperatorType.MOD,3);
        outStackPriorityMap.put(OperatorType.DIV,3);
        outStackPriorityMap.put(OperatorType.MUL,3);
        outStackPriorityMap.put(OperatorType.ADD,4);
        outStackPriorityMap.put(OperatorType.SUB,4);
        outStackPriorityMap.put(OperatorType.LEFTSHIFTING,5);
        outStackPriorityMap.put(OperatorType.RIGHTSHIFTING,5);
        outStackPriorityMap.put(OperatorType.LESSTHAN,6);
        outStackPriorityMap.put(OperatorType.LESSTHANOREQUAL,6);
        outStackPriorityMap.put(OperatorType.GREATERTHAN,6);
        outStackPriorityMap.put(OperatorType.GREATERTHANOREQUAL,6);
        outStackPriorityMap.put(OperatorType.EQUAL,7);
        outStackPriorityMap.put(OperatorType.NOTEQUAL,7);
        outStackPriorityMap.put(OperatorType.BITWISEAND,8);
        outStackPriorityMap.put(OperatorType.BITWISEXOR,9);
        outStackPriorityMap.put(OperatorType.BITWISEOR,10);
        outStackPriorityMap.put(OperatorType.LOGICALAND,11);
        outStackPriorityMap.put(OperatorType.LOGICALOR,12);
        outStackPriorityMap.put(OperatorType.ASSIGN,14);
        outStackPriorityMap.put(OperatorType.ADDASSIGN,14);
        outStackPriorityMap.put(OperatorType.SUBASSIGN,14);
        outStackPriorityMap.put(OperatorType.MULASSIGN,14);
        outStackPriorityMap.put(OperatorType.DIVASSIGN,14);
        outStackPriorityMap.put(OperatorType.MODASSIGN,14);
        outStackPriorityMap.put(OperatorType.BITWISEANDASSIGN,14);
        outStackPriorityMap.put(OperatorType.BITWISEORASSIGN,14);
        outStackPriorityMap.put(OperatorType.BITWISEXORASSIGN,14);
        outStackPriorityMap.put(OperatorType.LEFTSHIFTINGASSIGN,14);
        outStackPriorityMap.put(OperatorType.RIGHTSHIFTINGASSIGN,14);
        outStackPriorityMap.put(OperatorType.COMMA,15);
        outStackPriorityMap.put(OperatorType.RIGHTPARENTHESES,16);
        outStackPriorityMap.put(OperatorType.RIGHTBRACKET,16);

        inStackPriorityMap = (HashMap<OperatorType, Integer>) outStackPriorityMap.clone();
        inStackPriorityMap.put(OperatorType.LEFTPARENTHESES,16);
        inStackPriorityMap.put(OperatorType.LEFTBRACKET,16);
        inStackPriorityMap.put(OperatorType.RIGHTPARENTHESES,1);
        inStackPriorityMap.put(OperatorType.RIGHTBRACKET,1);

    }

    public Operator() {
        tokenType = TokenType.OPERATOR;
    }

    public static boolean isPrioriThan(Operator outStackOp, Operator inStackOp) {
        return outStackPriorityMap.get(outStackOp.getOperatorType()) < inStackPriorityMap.get(inStackOp.getOperatorType());
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }
}
