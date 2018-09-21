package main;

import exception.AcragaException;
import token.ReturnValue;
import token.Token;
import java.util.Stack;

public class Interpreter {
	private InputReader input;
	private AcragaException error;
	
	public Interpreter(InputReader inputReader) {
		input = inputReader;
		error = new AcragaException(inputReader);
	}
	
	public int run() {
		
		
		
		// return code
		// 0 represents the program ends normally without errors
		return 0;
	}
	
	private ReturnValue expression() {
		input.nextNotWhiteSpace();
		if(input.iseof())
			error.syntax("missing expression");
		
		Stack<Token> stack = new Stack<Token>();
		
		
		return null;
	}
}
