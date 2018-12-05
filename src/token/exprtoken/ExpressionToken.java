package token.exprtoken;

import component.Executable;
import token.Token;

public abstract class ExpressionToken extends Token implements Executable {
    public ExpressionToken() {
        super();
    }

    public ExpressionToken(ExpressionToken otherToken) {
        super(otherToken);
    }

    protected static void printWithIndent(StringBuilder sb, int number, String msg) {
        for (int i = 0; i < number; i++)
            sb.append(" ");
        sb.append(msg);
        sb.append("\n");
    }

    public abstract void print(StringBuilder sb, int indent);
}
