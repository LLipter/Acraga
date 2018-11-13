package token.exprtoken.operator.unary;

import component.context.DataStack;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.OperatorType;
import type.ValueType;

import java.math.BigInteger;

public class SelfIncrement extends UnaryOperator {

    public SelfIncrement() {
        operatorType = OperatorType.SELFINCREMENT;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {
        Value value = child.execute(context);
        if (value.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        if (!value.isInt())
            throw new RTException(getLines(), getPos(), "only integer numbers are allowed to do self_increment operation");
        BigInteger number = value.getIntValue();
        Value res = new Value(ValueType.INTEGER);
        res.setIntValue(number.add(BigInteger.ONE));
        context.setValue((Identifier) child, res);
        return res;
    }

}
