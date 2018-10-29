package token.operator.binary.bitwise;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.Identifier;
import token.Value;
import token.operator.binary.BinaryOperator;
import type.OperatorType;
import type.TokenType;
import type.ValueType;

import java.math.BigInteger;

public class RightShiftingAssign extends BinaryOperator {

    public RightShiftingAssign(){
        operatorType = OperatorType.RIGHTSHIFTINGASSIGN;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        if (lChild.getTokenType() != TokenType.IDENTIFIER)
            throw new RTException(getLines(), getPos(), "left value required");
        Value lvalue = lChild.execute(context);
        Value rvalue = rChild.execute(context);

        if(lvalue.isVoid() || rvalue.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");

        if(!rvalue.isInt() || !lvalue.isInt())
            throw new RTException(getLines(), getPos(), "only integer numbers are allowed to do bitwise operation");
        BigInteger number1 = lvalue.getIntValue();
        BigInteger number2 = rvalue.getIntValue();
        Value res = new Value(ValueType.INTEGER);
        res.setIntValue(number1.shiftRight(number2.intValue()));

        context.setValue((Identifier)lChild, res);

        return res;
    }
}
