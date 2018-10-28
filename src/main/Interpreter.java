package main;

import exception.Runtime;
import component.function.Function;
import component.function.FunctionSignature;
import component.statement.Statement;
import token.Value;

import java.util.HashMap;

public class Interpreter {

    private Parser parser;

    public Interpreter(Parser parser) throws Runtime {
        this.parser = parser;
        interpret();
    }

    private void interpret() throws Runtime {
        Value exitCode = runFunction(FunctionSignature.mainFunctionSignature);
        String format = "Program ends with '%";
        if (exitCode.isInt())
            System.out.println(String.format(format + "d'", exitCode.getIntValue()));
        else if (exitCode.isDouble())
            System.out.println(String.format(format + "f'", exitCode.getDoubleValue()));
        else if (exitCode.isBool())
            System.out.println(String.format(format + "b'", exitCode.getBoolValue()));
        else if (exitCode.isString())
            System.out.println(String.format(format + "s'", exitCode.getStringValue()));
        else
            System.out.println("unknown exit code type");
    }


    private Value runFunction(FunctionSignature signature) throws Runtime {
        HashMap<FunctionSignature, Function> functionMap = parser.getFunctionMap();
        if (!functionMap.containsKey(signature))
            throw new Runtime(String.format("function '%s' not found", signature));
        Function function = functionMap.get(FunctionSignature.mainFunctionSignature);
        Value retValue = null;
        for (Statement statement : function)
            retValue = runStatement(statement);
        // TODO: cast retValue to function return type
        return retValue;
    }

    private Value runStatement(Statement statement) {
        // TODO: interpret statement

        return null;
    }
}
