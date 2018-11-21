package component.statement;

import component.Executable;
import type.StatementType;


public abstract class Statement implements Executable {

    protected StatementType statementType;

    protected static void printWithIndent(StringBuilder sb, int number, String msg) {
        for (int i = 0; i < number; i++)
            sb.append(" ");
        sb.append(msg);
        sb.append("\n");
    }

    public abstract void print(StringBuilder sb, int indent);


}
