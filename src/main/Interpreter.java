package main;

import exception.SyntaxException;
import node.Function;
import node.FunctionSignature;
import token.Token;
import token.Value;

import java.util.HashMap;
import java.util.Stack;

public class Interpreter {
	
	private Scanner scanner;
	private HashMap<FunctionSignature, Function> functionMap;
	
	public Interpreter(InputReader inputReader) throws SyntaxException {
		scanner = new Scanner(inputReader);
		functionMap = new HashMap<FunctionSignature, Function>();
		interpret();
	}
	
	private void interpret() throws SyntaxException {
		while(!scanner.iseof()) {
			Function function = detectFunction();
			if(function == null) {
				int lines = scanner.getToken().getLines();
				int pos = scanner.getToken().getPos();
				
			}
			functionMap.put(function.getFunctionSignature(), function);
		}
		
		if(!functionMap.containsKey(FunctionSignature.mainFunctionSignature))
			throw new SyntaxException("main function not found");
		Function main = functionMap.get(FunctionSignature.mainFunctionSignature);
		Value exitCode = main.run();
		String format = "Program ends with '%";
		if(exitCode.isInt())
			System.out.println(String.format(format + "d'", exitCode.getIntValue()));
		else if(exitCode.isDouble())
			System.out.println(String.format(format + "f'", exitCode.getDoubleValue()));
		else if(exitCode.isBool())
			System.out.println(String.format(format + "b'", exitCode.getBoolValue()));
		else if(exitCode.isString())
			System.out.println(String.format(format + "s'", exitCode.getStringValue()));
		else
			System.out.println("unknown exit code type");
	}
	
	private Function detectFunction() {
		return null;
	}
}
