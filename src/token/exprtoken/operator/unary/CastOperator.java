package token.exprtoken.operator.unary;

import component.context.DataStack;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.Value;
import type.Casting;
import type.OperatorType;
import type.ValueType;

public class CastOperator extends UnaryOperator {
    private ValueType desType;

    public CastOperator() {
        operatorType = OperatorType.CASTING;
    }

    public ValueType getDesType() {
        return desType;
    }

    public void setDesType(ValueType desType) {
        this.desType = desType;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {
        Value value = child.execute(context);
        //if(value == null)
        //    throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        //TODO:add this in casting.
        return Casting.casting(value, desType);
    }
}
