package exception;

public class RTException extends AcragaException {

    public RTException(int lines, int pos, String msg) {
        super("RTException", lines, pos, msg);
    }

    public RTException(int lines, String msg) {
        this(lines, -1, msg);
    }

    public RTException(String msg) {
        this(-1, -1, msg);
    }

    public RTException() {
        this(-1, -1, "Unknown error");
    }
}

