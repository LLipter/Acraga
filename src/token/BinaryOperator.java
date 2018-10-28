package token;

import component.Expression;
import type.OperatorType;

public class BinaryOperator extends Operator {

    private ExpressionToken lChild;
    private ExpressionToken rChild;

    public BinaryOperator(OperatorType type) {
        super();
        operatorType = type;
    }

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
