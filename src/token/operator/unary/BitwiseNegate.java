package token.operator.unary;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.Value;
import type.OperatorType;
import type.ValueType;

import java.math.BigInteger;

public class BitwiseNegate extends UnaryOperator {

    public BitwiseNegate(){
        operatorType = OperatorType.BITWISENEGATE;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        Value value = child.execute(context);

        if(value.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");

        if(!value.isInt())
            throw new RTException(getLines(), getPos(), "only integer numbers are allowed to do bitwise operation");
        BigInteger number = value.getIntValue();
        Value res = new Value(ValueType.INTEGER);
        res.setIntValue(number.not());

        return res;
    }
}
