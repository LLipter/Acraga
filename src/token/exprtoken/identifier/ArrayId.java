package token.exprtoken.identifier;

import component.signal.ControlSignal;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.ExpressionToken;
import token.exprtoken.Value;

public class ArrayId extends Identifier {

    private ExpressionToken index = null;
    private ExpressionToken length = null;
    private int intIndex;

    public int getIntIndex() {
        return intIndex;
    }

    public void setIntIndex(int intIndex) {
        this.intIndex = intIndex;
    }

    public ExpressionToken getLength() {
        return length;
    }

    public void setLength(ExpressionToken length) {
        this.length = length;
    }

    public ExpressionToken getIndex() {
        return index;
    }

    public void setIndex(ExpressionToken ex) {
        index = ex;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {
        Value idx = index.execute(context);
        if (!idx.isInt())
            throw new RTException(getLines(), getPos(), "only integer can be used as index");
        intIndex = idx.getIntValue().intValue();
        return context.getValue(this);
    }

    @Override
    public void print(int indent) {
        if (index != null) {
            printWithIndent(indent, String.format("<ArrayId,%s>", id));
            printWithIndent(indent, "[Index]");
            index.print(indent + 4);
            printWithIndent(indent, "[End of Index]");
        } else {
            printWithIndent(indent, "{!!!MISSING INDEX!!!}");
        }

    }
}
