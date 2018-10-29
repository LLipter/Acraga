package token.exprtoken;

import component.Executable;
import token.Token;

public abstract class ExpressionToken extends Token implements Executable {
    protected static void printWithIndent(int number, String msg) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < number; i++)
            sb.append(" ");
        sb.append(msg);
        System.out.println(sb.toString());
    }

    public abstract void print(int indent);
}
