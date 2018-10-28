package token;

import component.context.DataStack;
import exception.RTException;

public class ArrayId extends Identifier {
    private ExpressionToken index;

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
    public Value execute(DataStack context) throws RTException {
        Value idx = index.execute(context);
        if (!idx.isInt())
            throw new RTException(getLines(),getPos(),"only integer can be used as index");
        return context.getValue(this, idx.getIntValue().intValue());
    }
}
