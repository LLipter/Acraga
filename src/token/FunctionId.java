package token;

import java.util.LinkedList;

//used when calling an existing function
public class FunctionId extends Identifier {
    private LinkedList<ExpressionToken> parameters;

    public FunctionId(String functionid){
        super();
        id = functionid;
        parameters =new LinkedList<>();
    }

    public void addParameter(ExpressionToken e){
        parameters.addLast(e);
    }

    public ExpressionToken getParameter(int i){
        if(parameters.size() < i+1)
            return null;
        return parameters.get(i);
    }

    @Override
    public String toString() {
        return String.format("<FunctionId,%s>", id);
    }
}
