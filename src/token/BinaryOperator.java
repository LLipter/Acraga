package token;

import node.Expression;
import type.OperatorType;

public class BinaryOperator extends Operator {

    private Token operand1;
    private Token operand2;

    private ExpressionToken lChild;
    private ExpressionToken rChild;

    public BinaryOperator(OperatorType type) {
        super();
        operatorType = type;
    }

    public void setOp1(Token op1) {
        operand1 = op1;
    }

    public void setOp2(Token op2) {
        operand2 = op2;
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
