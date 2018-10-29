package token.exprtoken.operator.binary.bitwise;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.identifier.Identifier;
import token.exprtoken.Value;
import token.exprtoken.operator.binary.BinaryOperator;
import type.OperatorType;
import type.TokenType;
import type.ValueType;

import java.math.BigInteger;

public class BitwiseOrAssign extends BinaryOperator {

    public BitwiseOrAssign(){
        operatorType = OperatorType.BITWISEORASSIGN;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        if (lChild.getTokenType() != TokenType.IDENTIFIER)
            throw new RTException(getLines(), getPos(), "left value required");
        Value rvalue = rChild.execute(context);
        Value lvalue = lChild.execute(context);

        if(lvalue.isVoid() || rvalue.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        if(!rvalue.isInt() || !lvalue.isInt())
            throw new RTException(getLines(), getPos(), "only integer numbers are allowed to do bitwise operation");
        BigInteger number1 = lvalue.getIntValue();
        BigInteger number2 = rvalue.getIntValue();
        Value res = new Value(ValueType.INTEGER);
        res.setIntValue(number1.or(number2));
        context.setValue((Identifier)lChild, res);
        return res;
    }
}
