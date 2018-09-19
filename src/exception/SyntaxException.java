package exception;

import main.InputReader;

public class SyntaxException {
	
	public static void stop(InputReader input, String msg) {
		System.err.printf("line %d, position %d : %s\n", input.getLine(), input.getPos(), msg);
		System.exit(1);
	}

}
