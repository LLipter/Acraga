package token.exprtoken.identifier;

import component.ReturnValue;
import component.context.DataStack;
import component.function.Function;
import component.function.FunctionSignature;
import exception.RTException;
import token.exprtoken.ExpressionToken;
import token.exprtoken.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

// used when calling an existing function
public class FunctionId extends Identifier {
    private LinkedList<ExpressionToken> parameters;

    public FunctionId() {
        super();
        parameters = new LinkedList<>();
    }

    public void addParameter(ExpressionToken e) {
        parameters.addLast(e);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(id);
        sb.append("(");
        for (ExpressionToken extoken : parameters) {
            sb.append(extoken.toString());
            sb.append(",");
        }
        if (parameters.size() > 0)
            sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return String.format("<FunctionId,%s>", sb.toString());
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        ArrayList<Value> arguments = new ArrayList<>();
        for (ExpressionToken para : parameters)
            arguments.add(para.execute(context));
        FunctionSignature functionSignature = new FunctionSignature(id);
        for (Value argu : arguments)
            functionSignature.addParameters(argu.getValueType());
        HashMap<FunctionSignature, Function> functionMap = context.getFunctionMap();
        if (!functionMap.containsKey(functionSignature))
            throw new RTException(getLines(), getPos(), "undefined function");
        Function func = functionMap.get(functionSignature);
        func.setArguments(arguments);
        return func.execute(context);
    }
}
