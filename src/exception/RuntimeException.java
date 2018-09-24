package exception;

public class RuntimeException extends AcragaException {

    public RuntimeException(int lines, int pos, String msg) {
        super("Runtime Exception", lines, pos, msg);
    }

    public RuntimeException(int lines, String msg) {
        this(lines, -1, msg);
    }

    public RuntimeException(String msg) {
        this(-1, -1, msg);
    }

    public RuntimeException() {
        this(-1, -1, "Unknown error");
    }
}

