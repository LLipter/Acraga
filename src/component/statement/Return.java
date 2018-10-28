package component.statement;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.ExpressionToken;
import token.Value;
import type.StatementType;

public class Return extends Statement {

    private ExpressionToken returnValue;

    public Return(){
        statementType = StatementType.RETURN;
    }

    public ExpressionToken getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ExpressionToken returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public Value execute(DataStack context) throws RTException,ReturnValue {
        Value retValue = returnValue.execute(context);
        throw new ReturnValue(retValue);
    }
}
