package token.operator.binary.comparison;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.Value;
import token.operator.binary.BinaryOperator;
import type.Casting;
import type.OperatorType;
import type.ValueType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class GreaterThan extends BinaryOperator {
    public GreaterThan(){
        operatorType = OperatorType.GREATERTHAN;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        Value lvalue = lChild.execute(context);
        Value rvalue = rChild.execute(context);

        Value res = new Value(ValueType.BOOLEAN);
        if(lvalue.isVoid() || rvalue.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        // String comparision
        if (lvalue.isString() && rvalue.isString()){
            String str1 = lvalue.getStringValue();
            String str2 = rvalue.getStringValue();
            if (str1.compareTo(str2) == 1)
                res.setBoolValue(true);
            else
                res.setBoolValue(false);
        }
        else if(lvalue.isString() || rvalue.isString())
            throw new RTException(getLines(), getPos(), "string variable cannot be compared with non-string variable");
        // automatically promote to double
        else if(lvalue.isDouble() || rvalue.isDouble()){
            BigDecimal number1 = Casting.casting(lvalue,ValueType.BOOLEAN).getDoubleValue();
            BigDecimal number2 = Casting.casting(rvalue,ValueType.BOOLEAN).getDoubleValue();
            if (number1.compareTo(number2) == 1)
                res.setBoolValue(true);
            else
                res.setBoolValue(false);
        }
        // automatically promote to integer
        else if(lvalue.isInt() || rvalue.isInt()){
            BigInteger number1 = Casting.casting(lvalue,ValueType.INTEGER).getIntValue();
            BigInteger number2 = Casting.casting(rvalue,ValueType.INTEGER).getIntValue();
            if (number1.compareTo(number2) == 1)
                res.setBoolValue(true);
            else
                res.setBoolValue(false);
        }
        else
            throw new RTException(getLines(), getPos(), "boolean variable cannot be compared with another boolean variable");
        return res;
    }
}
