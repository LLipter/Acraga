package token.exprtoken.operator.unary;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.Value;
import type.OperatorType;

public class NegativeSign extends UnaryOperator {


    public NegativeSign(){
        operatorType = OperatorType.NEGATIVESIGN;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        Value value = child.execute(context);
        if(value.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        else if(value.isString())
            throw new RTException(getLines(), getPos(), "missing string operand");
        else if(value.isBool())
            value.setBoolValue(!value.getBoolValue());
        else if(value.isInt())
            value.setIntValue(value.getIntValue().negate());
        else
            value.setDoubleValue(value.getDoubleValue().negate());
        return value;
    }
}
