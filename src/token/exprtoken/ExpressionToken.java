package token.exprtoken;

import component.Executable;
import component.statement.Expression;
import token.Token;

public abstract class ExpressionToken extends Token implements Executable {
    protected static void printWithIndent(StringBuilder sb, int number, String msg) {
        for (int i = 0; i < number; i++)
            sb.append(" ");
        sb.append(msg);
        sb.append("\n");
    }

    public ExpressionToken(){
        super();
    }

    public ExpressionToken(ExpressionToken otherToken){
        super(otherToken);
    }

    public abstract void print(StringBuilder sb, int indent);
}
