package token.exprtoken.operator.binary.comparison;

import component.context.DataStack;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.operator.binary.BinaryOperator;
import type.Casting;
import type.OperatorType;
import type.ValueType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LessThan extends BinaryOperator {
    public LessThan() {
        operatorType = OperatorType.LESSTHAN;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {
        Value lvalue = lChild.execute(context);
        Value rvalue = rChild.execute(context);

        Value res = new Value(ValueType.BOOLEAN);
        if (lvalue.isVoid() || rvalue.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        // String comparision
        if (lvalue.isString() && rvalue.isString()) {
            String str1 = lvalue.getStringValue();
            String str2 = rvalue.getStringValue();
            if (str1.compareTo(str2) < 0)
                res.setBoolValue(true);
            else
                res.setBoolValue(false);
        } else if (lvalue.isString() || rvalue.isString())
            throw new RTException(getLines(), getPos(), "string variable cannot be compared with non-string variable");
            // automatically promote to double
        else if (lvalue.isDouble() || rvalue.isDouble()) {
            BigDecimal number1 = Casting.casting(lvalue, ValueType.DOUBLE).getDoubleValue();
            BigDecimal number2 = Casting.casting(rvalue, ValueType.DOUBLE).getDoubleValue();
            if (number1.compareTo(number2) < 0)
                res.setBoolValue(true);
            else
                res.setBoolValue(false);
        }
        // automatically promote to integer
        else if (lvalue.isInt() || rvalue.isInt()) {
            BigInteger number1 = Casting.casting(lvalue, ValueType.INTEGER).getIntValue();
            BigInteger number2 = Casting.casting(rvalue, ValueType.INTEGER).getIntValue();
            if (number1.compareTo(number2) < 0)
                res.setBoolValue(true);
            else
                res.setBoolValue(false);
        } else
            throw new RTException(getLines(), getPos(), "boolean variable cannot do comparison operation");
        return res;
    }
}
