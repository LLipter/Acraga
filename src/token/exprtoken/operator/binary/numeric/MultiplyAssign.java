package token.exprtoken.operator.binary.numeric;

import component.context.DataStack;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import token.exprtoken.operator.binary.BinaryOperator;
import type.Casting;
import type.OperatorType;
import type.TokenType;
import type.ValueType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MultiplyAssign extends BinaryOperator {

    public MultiplyAssign() {
        operatorType = OperatorType.MULASSIGN;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {
        if (lChild.getTokenType() != TokenType.IDENTIFIER)
            throw new RTException(getLines(), getPos(), "left value required");
        Value rvalue = rChild.execute(context);
        Value lvalue = lChild.execute(context);

        Value res;
        if (lvalue.isVoid() || rvalue.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        if (lvalue.isString() || rvalue.isString())
            throw new RTException(getLines(), getPos(), "string variable cannot do multiplication operation");
            // automatically promote to double
        else if (lvalue.isDouble() || rvalue.isDouble()) {
            res = new Value(ValueType.DOUBLE);
            BigDecimal number1 = Casting.casting(lvalue, ValueType.DOUBLE).getDoubleValue();
            BigDecimal number2 = Casting.casting(rvalue, ValueType.DOUBLE).getDoubleValue();
            BigDecimal mul = number1.multiply(number2);
            res.setDoubleValue(mul);
        }
        // automatically promote to integer
        else if (lvalue.isInt() || rvalue.isInt()) {
            res = new Value(ValueType.INTEGER);
            BigInteger number1 = Casting.casting(lvalue, ValueType.INTEGER).getIntValue();
            BigInteger number2 = Casting.casting(rvalue, ValueType.INTEGER).getIntValue();
            BigInteger mul = number1.multiply(number2);
            res.setIntValue(mul);
        } else
            throw new RTException(getLines(), getPos(), "boolean variable cannot do multiplication operation");
        context.setValue((Identifier) lChild, res);
        return res;
    }
}
