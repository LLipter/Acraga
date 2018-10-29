package token.exprtoken.operator.binary.numeric;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.operator.binary.BinaryOperator;
import type.Casting;
import type.OperatorType;
import type.ValueType;

import java.math.BigInteger;

public class Reminder extends BinaryOperator {

    public Reminder() {
        operatorType = OperatorType.MOD;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        Value lvalue = lChild.execute(context);
        Value rvalue = rChild.execute(context);

        Value res;
        if (lvalue.isVoid() || rvalue.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        if (lvalue.isString() || rvalue.isString())
            throw new RTException(getLines(), getPos(), "string variable cannot do mod operation");
        else if (lvalue.isDouble() || rvalue.isDouble())
            throw new RTException(getLines(), getPos(), "double variable cannot do mod operation");
            // automatically promote to integer
        else if (lvalue.isInt() || rvalue.isInt()) {
            res = new Value(ValueType.INTEGER);
            BigInteger number1 = Casting.casting(lvalue, ValueType.INTEGER).getIntValue();
            BigInteger number2 = Casting.casting(rvalue, ValueType.INTEGER).getIntValue();
            if (number2.compareTo(BigInteger.ZERO) == 0)
                throw new RTException(getLines(), getPos(), "cannot mod by zero");
            BigInteger mod = number1.remainder(number2);
            res.setIntValue(mod);
        } else
            throw new RTException(getLines(), getPos(), "boolean variable cannot do mod operation");
        return res;
    }
}
