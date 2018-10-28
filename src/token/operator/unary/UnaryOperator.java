package token.operator.unary;

import token.ExpressionToken;
import token.operator.Operator;
import type.OperatorType;

public abstract class UnaryOperator extends Operator {

    protected ExpressionToken child;

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
