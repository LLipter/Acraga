package token.operator;

import token.ExpressionToken;
import type.OperatorType;

public abstract class UnaryOperator extends Operator {

    private ExpressionToken child;

    public UnaryOperator(OperatorType type) {
        super();
        operatorType = type;
    }

    @Override
    public String toString() {
        return String.format("<UnaryOperator,%s>", operatorType);
    }

    public void setChild(ExpressionToken ExToken){
        child = ExToken;
    }

    public ExpressionToken getChild() {
        return child;
    }
}
