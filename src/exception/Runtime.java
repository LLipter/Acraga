package exception;

public class Runtime extends AcragaException {

    public Runtime(int lines, int pos, String msg) {
        super("Runtime Exception", lines, pos, msg);
    }

    public Runtime(int lines, String msg) {
        this(lines, -1, msg);
    }

    public Runtime(String msg) {
        this(-1, -1, msg);
    }

    public Runtime() {
        this(-1, -1, "Unknown error");
    }
}

