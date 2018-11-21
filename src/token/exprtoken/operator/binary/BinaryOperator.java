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
    public void print(StringBuilder sb, int indent) {
        printWithIndent(sb, indent, String.format("[BinaryOperator] %s", operatorType));
        printWithIndent(sb, indent, "[Left Operand]");
        lChild.print(sb, indent + 4);
        printWithIndent(sb, indent, "[End of Left Operand]");
        printWithIndent(sb, indent, "[Right Operand]");
        rChild.print(sb, indent + 4);
        printWithIndent(sb, indent, "[End of Right Operand]");
        printWithIndent(sb, indent, String.format("[End of BinaryOperator] %s", operatorType));
    }
}
