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
        //TODO:add this in casting.
        Value newValue = Casting.casting(value, desType);
        if (newValue == null)
            throw new RTException(value.getLines(), value.getPos(), "cannot cast from type " + value.getValueType() + " of this value to type " + desType.toString());
        return newValue;
    }
}
