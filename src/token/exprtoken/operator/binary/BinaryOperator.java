package token.exprtoken.operator.binary;

import token.exprtoken.ExpressionToken;
import token.exprtoken.operator.Operator;

public abstract class BinaryOperator extends Operator {

    protected ExpressionToken lChild;
    protected ExpressionToken rChild;

    public ExpressionToken getlChild() {
        return lChild;
    }

    public void setlChild(ExpressionToken ExToken) {
        lChild = ExToken;
    }

    public ExpressionToken getrChild() {
        return rChild;
    }

    public void setrChild(ExpressionToken ExToken) {
        rChild = ExToken;
    }

    @Override
    public String toString() {
        return String.format("<BinaryOperator,%s>", operatorType);
    }
}
