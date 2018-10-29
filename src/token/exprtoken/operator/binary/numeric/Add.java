package token.exprtoken.operator.binary.numeric;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.operator.binary.BinaryOperator;
import type.Casting;
import type.OperatorType;
import type.ValueType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Add extends BinaryOperator {

    public Add() {
        operatorType = OperatorType.ADD;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        Value lvalue = lChild.execute(context);
        Value rvalue = rChild.execute(context);

        Value res;
        if (lvalue.isVoid() || rvalue.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        // String concatenation
        if (lvalue.isString() || rvalue.isString()) {
            res = new Value(ValueType.STRING);
            String str = Casting.casting(lvalue, ValueType.STRING).getStringValue()
                    + Casting.casting(rvalue, ValueType.STRING).getStringValue();
            res.setStringValue(str);
        }
        // automatically promote to double
        else if (lvalue.isDouble() || rvalue.isDouble()) {
            res = new Value(ValueType.DOUBLE);
            BigDecimal number1 = Casting.casting(lvalue, ValueType.DOUBLE).getDoubleValue();
            BigDecimal number2 = Casting.casting(rvalue, ValueType.DOUBLE).getDoubleValue();
            BigDecimal sum = number1.add(number2);
            res.setDoubleValue(sum);
        }
        // automatically promote to integer
        else if (lvalue.isInt() || rvalue.isInt()) {
            res = new Value(ValueType.INTEGER);
            BigInteger number1 = Casting.casting(lvalue, ValueType.INTEGER).getIntValue();
            BigInteger number2 = Casting.casting(rvalue, ValueType.INTEGER).getIntValue();
            BigInteger sum = number1.add(number2);
            res.setIntValue(sum);
        } else
            throw new RTException(getLines(), getPos(), "boolean variable cannot do add operation");
        return res;
    }
}
