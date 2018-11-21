package component.statement;

import component.context.DataStack;
import component.signal.BreakRequest;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.Value;

public class Break extends Statement {

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
        BreakRequest breakRequest = new BreakRequest();
        breakRequest.setLine(line);
        breakRequest.setPos(pos);
        throw breakRequest;
    }

    @Override
    public void print(StringBuilder sb, int indent) {
        printWithIndent(sb, indent, "[Break Statement]");
    }
}
