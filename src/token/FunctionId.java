package token;

import java.util.LinkedList;

//used when calling an existing function
public class FunctionId extends Identifier {
    private String fid;
    private LinkedList<ExpressionToken> expressions;

    public FunctionId(String functionid){
        fid=functionid;
        expressions=new LinkedList<>();
    }

    public void addExpression(ExpressionToken e){
        expressions.addLast(e);
    }

    @Override
    public String toString() {
        return String.format("<FunctionId,%s>", fid);
    }
}
