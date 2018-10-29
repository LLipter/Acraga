package component.statement;

import component.Executable;
import type.StatementType;


public abstract class Statement implements Executable {

    protected StatementType statementType;

    public abstract void print(int indent);

    protected static void printWithIndent(int number, String msg){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<number;i++)
            sb.append(" ");
        sb.append(msg);
        System.out.println(sb.toString());
    }


}
