package token.exprtoken.operator.unary;

import token.exprtoken.ExpressionToken;
import token.exprtoken.operator.Operator;

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
