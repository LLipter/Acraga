package exception;

public class Syntax extends AcragaException {

    public Syntax(int lines, int pos, String msg) {
        super("Syntax Exception", lines, pos, msg);
    }

    public Syntax(int lines, String msg) {
        this(lines, -1, msg);
    }

    public Syntax(String msg) {
        this(-1, -1, msg);
    }

    public Syntax() {
        this(-1, -1, "Unknown error");
    }

}
