package token.exprtoken.identifier;

import component.context.DataStack;
import component.function.Function;
import component.function.FunctionSignature;
import component.signal.ControlSignal;
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
    public Value execute(DataStack context) throws RTException, ControlSignal {
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
        Value retValue;
        try {
            retValue = func.execute(context);
        } catch (RTException e) {
            throw new RTException(getLines(), getPos(), e.getMessage());
        }
        return retValue;
    }

    @Override
    public void print(StringBuilder sb, int indent) {
        printWithIndent(sb, indent, String.format("<FunctionId,%s>", id));
        printWithIndent(sb, indent, "[Arguments]");
        for (int i = 0; i < parameters.size(); i++) {
            printWithIndent(sb, indent, String.format("[Index %d]", i));
            parameters.get(i).print(sb, indent + 4);
            printWithIndent(sb, indent, String.format("[End of Index %d]", i));
        }
        printWithIndent(sb, indent, "[End of Arguments]");

    }
}
