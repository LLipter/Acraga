package component.statement;

import component.context.DataStack;
import component.signal.ContinueRequest;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.Value;

public class Continue extends Statement {
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

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {
        ContinueRequest continueRequest = new ContinueRequest();
        continueRequest.setLine(line);
        continueRequest.setPos(pos);
        throw continueRequest;
    }

    @Override
    public void print(int indent) {
        printWithIndent(indent, "[Continue Statement]");
    }
}
