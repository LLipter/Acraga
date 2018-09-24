package exception;

public class AcragaException extends Exception {

    protected String exceptionType;
    protected int lines;
    protected int pos;

    public AcragaException(String exceptionType, int lines, int pos, String msg) {
        super(msg);
        this.exceptionType = exceptionType;
        this.lines = lines;
        this.pos = pos;
    }

    public AcragaException(int lines, int pos, String msg) {
        this("Acraga Exception", lines, pos, msg);
    }

    public AcragaException(int lines, String msg) {
        this("Acraga Exception", lines, -1, msg);
    }

    public AcragaException(String msg) {
        this("Acraga Exception", -1, -1, msg);
    }

    public AcragaException() {
        this("Acraga Exception", -1, -1, "Unknown error");
    }

    @Override
    public String toString() {
        String msg;
        if (pos == -1 && lines == -1)
            msg = String.format("%s : %s", exceptionType, this.getMessage());
        else if (pos == -1 && lines != -1)
            msg = String.format("%s in line %d : %s", exceptionType, lines, this.getMessage());
        else
            msg = String.format("%s in line %d, position %d : %s", exceptionType, lines, pos, this.getMessage());
        return msg;
    }

}
