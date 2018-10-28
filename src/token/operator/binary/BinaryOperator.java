package token.operator.binary;

import token.ExpressionToken;
import token.operator.Operator;
import type.OperatorType;

public abstract class BinaryOperator extends Operator {

    protected ExpressionToken lChild;
    protected ExpressionToken rChild;

    @Override
    public String toString() {
        return String.format("<BinaryOperator,%s>", operatorType);
    }

    public void setlChild(ExpressionToken ExToken) {
        lChild = ExToken;
    }

    public void setrChild(ExpressionToken ExToken) {
        rChild = ExToken;
    }

    public ExpressionToken getlChild() {
        return lChild;
    }

    public ExpressionToken getrChild() {
        return rChild;
    }
}
