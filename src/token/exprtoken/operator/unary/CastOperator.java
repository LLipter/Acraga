package token.exprtoken.operator.unary;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.Value;
import type.Casting;
import type.ValueType;

public class CastOperator extends UnaryOperator{
    private ValueType desType;

    public ValueType getDesType() {
        return desType;
    }

    public void setDesType(ValueType desType) {
        this.desType = desType;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        Value value = child.execute(context);
        if(value == null)
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        return Casting.casting(value,desType);
    }
}
