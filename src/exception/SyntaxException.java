package exception;

public class SyntaxException extends AcragaException{
	
	public SyntaxException(int lines, int pos, String msg) {
		super("Syntax Exception", lines, pos, msg);
	}
	
	public SyntaxException(int lines, String msg) {
		this(lines, -1, msg);
	}
	
	public SyntaxException(String msg) {
		this(-1, -1, msg);
	}
	
	public SyntaxException() {
		this(-1, -1, "Unknown error");
	}

}
