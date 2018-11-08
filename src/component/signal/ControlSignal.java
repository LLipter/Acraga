package component.signal;

public abstract class ControlSignal extends Throwable {
    private int line;
    private int pos;
    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
