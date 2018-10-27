package token;

import java.util.LinkedList;

//used when calling an existing function
public class FunctionId extends Identifier {
    private LinkedList<ExpressionToken> parameters;

    public FunctionId(){
        super();
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
        StringBuffer sb = new StringBuffer();
        sb.append(id);
        sb.append("(");
        for(ExpressionToken extoken : parameters){
            sb.append(extoken.toString());
            sb.append(",");
        }
        if(parameters.size() > 0)
            sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return String.format("<FunctionId,%s>", sb.toString());
    }
}
