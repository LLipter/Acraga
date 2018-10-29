package component;

import token.exprtoken.Value;

public class ReturnValue extends Throwable {

    private Value returnValue;
    private int line;
    private int pos;

    public ReturnValue(Value value){
        returnValue = value;
    }

    public Value getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Value returnValue) {
        this.returnValue = returnValue;
    }

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
