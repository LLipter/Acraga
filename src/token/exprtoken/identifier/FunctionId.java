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
        return func.execute(context);
    }

    @Override
    public void print(int indent) {
        printWithIndent(indent, String.format("<FunctionId,%s>", id));
        printWithIndent(indent, "[Arguments]");
        for (int i = 0; i < parameters.size(); i++) {
            printWithIndent(indent, String.format("[Index %d]", i));
            parameters.get(i).print(indent + 4);
            printWithIndent(indent, String.format("[End of Index %d]", i));
        }
        printWithIndent(indent, "[End of Arguments]");

    }
}
