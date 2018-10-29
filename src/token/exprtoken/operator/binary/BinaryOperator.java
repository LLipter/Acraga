package token.exprtoken.operator.binary;

import token.exprtoken.ExpressionToken;
import token.exprtoken.operator.Operator;

public abstract class BinaryOperator extends Operator {

    protected ExpressionToken lChild;
    protected ExpressionToken rChild;

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

    @Override
    public String toString() {
        return String.format("<BinaryOperator,%s>", operatorType);
    }
}
