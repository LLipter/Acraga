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

    @Override
    public void print(int indent) {
        printWithIndent(indent, String.format("[BinaryOperator] %s", operatorType));
        printWithIndent(indent, "[Left Operand]");
        lChild.print(indent + 4);
        printWithIndent(indent, "[End of Left Operand]");
        printWithIndent(indent, "[Right Operand]");
        rChild.print(indent + 4);
        printWithIndent(indent, "[End of Right Operand]");
        printWithIndent(indent, String.format("[End of BinaryOperator] %s", operatorType));
    }
}
