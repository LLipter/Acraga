package token.operator.binary;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.Identifier;
import token.Value;
import type.Casting;
import type.OperatorType;
import type.TokenType;
import type.ValueType;

import java.math.BigInteger;

public class ReminderAssign extends BinaryOperator {

    public ReminderAssign(){
        operatorType = OperatorType.MODASSIGN;
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
            throw new RTException(getLines(), getPos(), "string variable cannot do mod operation");
        else if(lvalue.isDouble() || rvalue.isDouble())
            throw new RTException(getLines(), getPos(), "double variable cannot do mod operation");
            // automatically promote to integer
        else if(lvalue.isInt() || rvalue.isInt()){
            res = new Value(ValueType.INTEGER);
            BigInteger number1 = Casting.casting(lvalue,ValueType.INTEGER).getIntValue();
            BigInteger number2 = Casting.casting(rvalue,ValueType.INTEGER).getIntValue();
            if(number2.compareTo(BigInteger.ZERO) == 0)
                throw new RTException(getLines(), getPos(), "cannot mod by zero");
            BigInteger mod = number1.remainder(number2);
            res.setIntValue(mod);
        }
        else
            throw new RTException(getLines(), getPos(), "boolean variable cannot do mod operation");
        context.setValue((Identifier)lChild, res);
        return res;
    }
}
