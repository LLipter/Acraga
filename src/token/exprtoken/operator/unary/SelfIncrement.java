package token.exprtoken.operator.unary;

import component.context.DataStack;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.ArrayId;
import token.exprtoken.identifier.FunctionId;
import token.exprtoken.identifier.Identifier;
import type.OperatorType;
import type.ValueType;

import java.math.BigInteger;

public class SelfIncrement extends UnaryOperator {

    private boolean isPre;

    public SelfIncrement() {
        operatorType = OperatorType.SELFINCREMENT;
        this.isPre = true;
    }

    public boolean isPre() {
        return isPre;
    }

    public void setPre(boolean pre) {
        isPre = pre;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {
        if (!(child instanceof Identifier && !(child instanceof ArrayId) && !(child instanceof FunctionId)))
            throw new RTException(getLines(), getPos(), "only identifier is allowed to do self_increment");
        Value oldValue = child.execute(context);
        if (!oldValue.isInt())
            throw new RTException(getLines(), getPos(), "only integer numbers are allowed to do self_increment");
        BigInteger number = oldValue.getIntValue();
        Value newValue = new Value(ValueType.INTEGER);
        newValue.setIntValue(number.add(BigInteger.ONE));
        context.setValue((Identifier) child, newValue);
        if (isPre)
            return newValue;
        else
            return oldValue;

    }

}
