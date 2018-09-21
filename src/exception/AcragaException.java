package exception;

import main.InputReader;

public class AcragaException {
	
	private InputReader input;
	
	public AcragaException(InputReader reader) {
		input = reader;
	}
	
	public void syntax(String msg) {
		System.err.printf("Syntax error in line %d, position %d : %s\n", input.getLine(), input.getPos(), msg);
		System.exit(1);
	}

}
