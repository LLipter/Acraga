package token.exprtoken.identifier;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.ExpressionToken;
import token.exprtoken.Value;

public class ArrayId extends Identifier {
    private ExpressionToken index;
    private ExpressionToken length;
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

    public void setIndex(ExpressionToken ex){
        index=ex;
    }

    public ExpressionToken getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("<ArrayId,%s,%s>", id, index);
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        Value idx = index.execute(context);
        if (!idx.isInt())
            throw new RTException(getLines(),getPos(),"only integer can be used as index");
        intIndex = idx.getIntValue().intValue();
        return context.getValue(this);
    }
}
