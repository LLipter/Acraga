package main;

import component.ReturnValue;
import component.context.DataStack;
import component.function.Function;
import component.function.FunctionSignature;
import component.function.predefined.Print;
import component.statement.Statement;
import exception.AcragaException;
import exception.RTException;
import token.exprtoken.Value;

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

    public static void interpretProgram(String inpuFile) throws AcragaException, ReturnValue {
        // TODO: add try catch to catch all AcragaException
        Preprocessor preprocessor = new Preprocessor(inpuFile);
        Scanner scanner = new Scanner(preprocessor);
        Parser parser = new Parser(scanner);
        parser.parseProgram();
        Interpreter interpreter = new Interpreter(parser);
        interpreter.interpretProgram();
    }

    public static Value interpretExpression(String expression) {
        try {
            Preprocessor preprocessor = new Preprocessor(new StringReader(expression));
            Scanner scanner = new Scanner(preprocessor);
            Parser parser = new Parser(scanner);
            parser.parseExpression();
            Interpreter interpreter = new Interpreter(parser);
            return interpreter.interpretExpression();
        } catch (AcragaException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Value interpretExpression() {
        try {
            return parser.getExpressionRoot().execute(context);
        } catch (AcragaException e) {
            System.err.println(e.getMessage());
        } catch (ReturnValue returnValue) {
            // never used
        }
        // never used
        return null;
    }

    public void runGlobalStatements(LinkedList<Statement> globalStatements) throws RTException{
        if(!globalStatements.isEmpty()){
            Statement statement=globalStatements.pollFirst();
            try {
                while (statement != null) {
                    statement.execute(context);
                    statement = globalStatements.pollFirst();
                }
            }catch (ReturnValue retValue){
                throw new RTException(retValue.getLine(),retValue.getPos(),"cannot return in gloabl area");
            }
        }
    }

    public void interpretProgram() throws AcragaException {
        // TODO: add try catch to catch all AcragaException
        HashMap<FunctionSignature, Function> funcMap = parser.getFunctionMap();
        funcMap.putAll(predefinedFunction());
        context.setFunctionMap(funcMap);

        LinkedList<Statement> globalStatements=parser.getGlobalStatements();
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
        Print print = new Print();
        funcMap.put(print.getFunctionSignature(), print);
        return funcMap;
    }


}
