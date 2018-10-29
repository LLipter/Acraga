package component.statement;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.ExpressionToken;
import token.exprtoken.Value;
import type.StatementType;

public class Return extends Statement {

    private ExpressionToken returnValue;
    private int line;
    private int pos;

    public Return() {
        statementType = StatementType.RETURN;
    }

    public ExpressionToken getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ExpressionToken returnValue) {
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

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        Value retValue = returnValue.execute(context);
        ReturnValue v = new ReturnValue(retValue);
        v.setLine(line);
        v.setPos(pos);
        throw v;
    }
}
