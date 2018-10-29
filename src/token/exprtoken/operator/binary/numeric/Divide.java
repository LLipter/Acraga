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

public class Divide extends BinaryOperator {


    public Divide(){
        operatorType = OperatorType.DIV;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        Value lvalue = lChild.execute(context);
        Value rvalue = rChild.execute(context);

        Value res;
        if(lvalue.isVoid() || rvalue.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        if (lvalue.isString() || rvalue.isString())
            throw new RTException(getLines(), getPos(), "string variable cannot do divide operation");
        // automatically promote to double
        else if(lvalue.isDouble() || rvalue.isDouble()){
            res = new Value(ValueType.DOUBLE);
            BigDecimal number1 = Casting.casting(lvalue,ValueType.DOUBLE).getDoubleValue();
            BigDecimal number2 = Casting.casting(rvalue,ValueType.DOUBLE).getDoubleValue();
            if(number2.compareTo(BigDecimal.ZERO) == 0)
                throw new RTException(getLines(), getPos(), "cannot divided by zero");
            BigDecimal div = number1.divide(number2);
            res.setDoubleValue(div);
        }
        // automatically promote to integer
        else if(lvalue.isInt() || rvalue.isInt()){
            res = new Value(ValueType.INTEGER);
            BigInteger number1 = Casting.casting(lvalue,ValueType.INTEGER).getIntValue();
            BigInteger number2 = Casting.casting(rvalue,ValueType.INTEGER).getIntValue();
            if(number2.compareTo(BigInteger.ZERO) == 0)
                throw new RTException(getLines(), getPos(), "cannot divided by zero");
            BigInteger div = number1.divide(number2);
            res.setIntValue(div);
        }
        else
            throw new RTException(getLines(), getPos(), "boolean variable cannot be divided by another boolean variable");
        return res;
    }

}
