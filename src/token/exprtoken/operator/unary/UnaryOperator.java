package token.exprtoken.operator.unary;

import token.exprtoken.ExpressionToken;
import token.exprtoken.operator.Operator;

public abstract class UnaryOperator extends Operator {

    protected ExpressionToken child;

    public ExpressionToken getChild() {
        return child;
    }

    public void setChild(ExpressionToken ExToken) {
        child = ExToken;
    }

    @Override
    public String toString() {
        return String.format("<UnaryOperator,%s>", operatorType);
    }
}
