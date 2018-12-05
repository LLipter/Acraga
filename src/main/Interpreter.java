package main;


import component.context.DataStack;
import component.function.Function;
import component.function.FunctionSignature;
import component.function.predefined.io.Print;
import component.function.predefined.io.Read;
import component.function.predefined.math.Abs;
import component.function.predefined.math.Max;
import component.function.predefined.math.Min;
import component.signal.BreakRequest;
import component.signal.ContinueRequest;
import component.signal.ControlSignal;
import component.signal.ReturnValue;
import component.statement.Statement;
import exception.AcragaException;
import exception.RTException;
import gui.TwoTuple;
import token.exprtoken.Value;
import type.ValueType;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;

public class Interpreter {

    private Parser parser;
    private DataStack context;

    public Interpreter(Parser parser) throws RTException {
        this.parser = parser;
        context = new DataStack();
    }

    public static void interpretProgram(String inpuFile) {
        try {
            Preprocessor preprocessor = new Preprocessor(inpuFile);
            Scanner scanner = new Scanner(preprocessor);
            Parser parser = new Parser(scanner);
            parser.parseProgram();
            Interpreter interpreter = new Interpreter(parser);
            interpreter.interpretProgram();
        } catch (AcragaException e) {
            System.err.println(e.toString());
        }

    }

    public static TwoTuple interpretExpression(String expression) {
        try {
            Preprocessor preprocessor = new Preprocessor(new StringReader(expression));
            Scanner scanner = new Scanner(preprocessor);
            Parser parser = new Parser(scanner);
            parser.parseExpression();
            Interpreter interpreter = new Interpreter(parser);
            Value value = interpreter.interpretExpression();
            return new TwoTuple<>(value, parser.getSb());
        } catch (AcragaException e) {
            Value value = new Value(ValueType.STRING);
            String msg = e.toString().substring(e.toString().indexOf("position"));
            value.setStringValue(msg);
            return new TwoTuple<>(value, null);
        }

    }

    public Value interpretExpression() throws AcragaException {
        try {
            return parser.getExpressionRoot().execute(context);
        } catch (ControlSignal controlSignal) {
            controlSignal.printStackTrace();
        }
        // never used
        return null;
    }

    public void runGlobalStatements(LinkedList<Statement> globalStatements) throws RTException {
        if (!globalStatements.isEmpty()) {
            Statement statement = globalStatements.pollFirst();
            try {
                while (statement != null) {
                    statement.execute(context);
                    statement = globalStatements.pollFirst();
                }
            } catch (ReturnValue retValue) {
                throw new RTException(retValue.getLine(), retValue.getPos(), "cannot return in gloabl area");
            } catch (BreakRequest br) {
                throw new RTException(br.getLine(), br.getPos(), "cannot use break outside loop");
            } catch (ContinueRequest cr) {
                throw new RTException(cr.getLine(), cr.getPos(), "cannot use continue outside loop");
            } catch (ControlSignal cs) {
                //never used
            }
        }
    }

    public void interpretProgram() throws AcragaException {
        // TODO: add try catch to catch all AcragaException
        HashMap<FunctionSignature, Function> funcMap = parser.getFunctionMap();
        funcMap.putAll(predefinedFunction());
        context.setFunctionMap(funcMap);

        LinkedList<Statement> globalStatements = parser.getGlobalStatements();
        context.createFrame();
        runGlobalStatements(globalStatements);


        Value exitCode = runFunction(FunctionSignature.mainFunctionSignature);
        String format = "Program ends with %d";

        runGlobalStatements(globalStatements);

        System.out.println(String.format(format, exitCode.getIntValue().intValue()));
        context.releaseFrame();
    }


    private Value runFunction(FunctionSignature signature) throws RTException {
        HashMap<FunctionSignature, Function> functionMap = parser.getFunctionMap();
        if (!functionMap.containsKey(signature))
            throw new RTException(String.format("function '%s' not found", signature));
        Function function = functionMap.get(signature);
        return function.execute(context);
    }

    private HashMap<FunctionSignature, Function> predefinedFunction() {
        HashMap<FunctionSignature, Function> funcMap = new HashMap<>();
        Print printString = new Print(false, ValueType.STRING);
        funcMap.put(printString.getFunctionSignature(), printString);
        Print printInteger = new Print(false, ValueType.INTEGER);
        funcMap.put(printInteger.getFunctionSignature(), printInteger);
        Print printDouble = new Print(false, ValueType.DOUBLE);
        funcMap.put(printDouble.getFunctionSignature(), printDouble);
        Print printBoolean = new Print(false, ValueType.BOOLEAN);
        funcMap.put(printBoolean.getFunctionSignature(), printBoolean);
        Print printlnString = new Print(true, ValueType.STRING);
        funcMap.put(printlnString.getFunctionSignature(), printlnString);
        Print printlnInteger = new Print(true, ValueType.INTEGER);
        funcMap.put(printlnInteger.getFunctionSignature(), printlnInteger);
        Print printlnDouble = new Print(true, ValueType.DOUBLE);
        funcMap.put(printlnDouble.getFunctionSignature(), printlnDouble);
        Print printlnBoolean = new Print(true, ValueType.BOOLEAN);
        funcMap.put(printlnBoolean.getFunctionSignature(), printlnBoolean);
        Print println = new Print(true, ValueType.VOID);
        funcMap.put(println.getFunctionSignature(), println);

        Read readline = new Read();
        funcMap.put(readline.getFunctionSignature(), readline);
        Read readInt = new Read(ValueType.INTEGER);
        funcMap.put(readInt.getFunctionSignature(), readInt);
        Read readDouble = new Read(ValueType.DOUBLE);
        funcMap.put(readDouble.getFunctionSignature(), readDouble);
        Read readBool = new Read(ValueType.BOOLEAN);
        funcMap.put(readBool.getFunctionSignature(), readBool);
        Read readString = new Read(ValueType.STRING);
        funcMap.put(readString.getFunctionSignature(), readString);

        Abs absInt = new Abs(ValueType.INTEGER);
        funcMap.put(absInt.getFunctionSignature(), absInt);
        Abs absDouble = new Abs(ValueType.DOUBLE);
        funcMap.put(absDouble.getFunctionSignature(), absDouble);
        Max maxInt = new Max(ValueType.INTEGER);
        funcMap.put(maxInt.getFunctionSignature(), maxInt);
        Max maxDouble = new Max(ValueType.DOUBLE);
        funcMap.put(maxDouble.getFunctionSignature(), maxDouble);
        Min minInt = new Min(ValueType.INTEGER);
        funcMap.put(minInt.getFunctionSignature(), minInt);
        Min minDouble = new Min(ValueType.DOUBLE);
        funcMap.put(minDouble.getFunctionSignature(), minDouble);

        return funcMap;
    }
}
