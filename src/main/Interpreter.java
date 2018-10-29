package main;

import component.context.DataStack;
import component.function.Function;
import component.function.FunctionSignature;
import exception.RTException;
import token.exprtoken.Value;

import java.util.HashMap;

public class Interpreter {

    private Parser parser;
    private DataStack context;

    public Interpreter(Parser parser) throws RTException {
        this.parser = parser;
        context = new DataStack();
        context.setFunctionMap(parser.getFunctionMap());
        interpret();
    }

    private void interpret() throws RTException {
        Value exitCode = runFunction(FunctionSignature.mainFunctionSignature);
        String format = "Program ends with %d";
        System.out.println(String.format(format, exitCode.getIntValue().intValue()));
    }


    private Value runFunction(FunctionSignature signature) throws RTException {
        HashMap<FunctionSignature, Function> functionMap = parser.getFunctionMap();
        if (!functionMap.containsKey(signature))
            throw new RTException(String.format("function '%s' not found", signature));
        Function function = functionMap.get(FunctionSignature.mainFunctionSignature);
        return function.execute(context);
    }


}
