package token.operator.binary.numeric;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.Identifier;
import token.Value;
import token.operator.binary.BinaryOperator;
import type.Casting;
import type.OperatorType;
import type.TokenType;
import type.ValueType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SubtractAssign extends BinaryOperator {

    public SubtractAssign(){
        operatorType = OperatorType.SUBASSIGN;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        if (lChild.getTokenType() != TokenType.IDENTIFIER)
            throw new RTException(getLines(), getPos(), "left value required");
        Value lvalue = lChild.execute(context);
        Value rvalue = rChild.execute(context);

        Value res;
        if(lvalue.isVoid() || rvalue.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        if (lvalue.isString() || rvalue.isString())
            throw new RTException(getLines(), getPos(), "string variable cannot do subtract operation");
            // automatically promote to double
        else if(lvalue.isDouble() || rvalue.isDouble()){
            res = new Value(ValueType.DOUBLE);
            BigDecimal number1 = Casting.casting(lvalue,ValueType.DOUBLE).getDoubleValue();
            BigDecimal number2 = Casting.casting(rvalue,ValueType.DOUBLE).getDoubleValue();
            BigDecimal sub = number1.subtract(number2);
            res.setDoubleValue(sub);
        }
        // automatically promote to integer
        else if(lvalue.isInt() || rvalue.isInt()){
            res = new Value(ValueType.INTEGER);
            BigInteger number1 = Casting.casting(lvalue,ValueType.INTEGER).getIntValue();
            BigInteger number2 = Casting.casting(rvalue,ValueType.INTEGER).getIntValue();
            BigInteger sub = number1.subtract(number2);
            res.setIntValue(sub);
        }
        else
            throw new RTException(getLines(), getPos(), "boolean variable cannot be subtracted to another boolean variable");
        context.setValue((Identifier)lChild, res);
        return res;
    }
}
