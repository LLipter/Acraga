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

    @Override
    public void print(int indent) {
        printWithIndent(indent, String.format("[UnaryOperator] %s", operatorType));
        printWithIndent(indent, "[Operand]");
        child.print(indent + 4);
        printWithIndent(indent, "[Operand]");
        printWithIndent(indent, String.format("[End of UnaryOperator] %s", operatorType));
    }
}
