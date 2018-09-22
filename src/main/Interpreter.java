package main;

import exception.SyntaxException;
import node.Function;
import token.Token;

import java.util.HashMap;
import java.util.Stack;

public class Interpreter {
	
	private Scanner scanner;
	private HashMap<String, Function> functionMap;
	
	public Interpreter(InputReader inputReader) throws SyntaxException {
		scanner = new Scanner(inputReader);
		functionMap = new HashMap<String, Function>();
		init();
	}
	
	private void init() throws SyntaxException {
		while(!scanner.iseof()) {
			Function function = detectFunction();
			if(function == null) {
				int lines = scanner.getToken().getLines();
				int pos = scanner.getToken().getPos();
				throw new SyntaxException(lines, pos, "invalid function declaration");
			}
				
				
		}
	}
	
	private Function detectFunction() {
		
	}
}
