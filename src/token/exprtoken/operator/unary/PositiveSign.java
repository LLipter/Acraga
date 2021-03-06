package token.exprtoken.operator.unary;

import component.context.DataStack;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.Value;
import type.OperatorType;

public class PositiveSign extends UnaryOperator {

    public PositiveSign() {
        operatorType = OperatorType.POSITIVESIGN;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {
        Value value = child.execute(context);
        if (value.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        else if (value.isString())
            throw new RTException(getLines(), getPos(), "missing string operand");
        else
            return value;
    }
}
