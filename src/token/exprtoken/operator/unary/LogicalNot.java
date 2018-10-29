package token.exprtoken.operator.unary;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.Value;
import type.Casting;
import type.OperatorType;
import type.ValueType;

public class LogicalNot extends UnaryOperator {
    public LogicalNot(){
        operatorType = OperatorType.LOGICALNOT;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        Value value = child.execute(context);

        Value res = new Value(ValueType.BOOLEAN);
        if(value.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        boolean bool = Casting.casting(value, ValueType.BOOLEAN).getBoolValue();
        res.setBoolValue(!bool);
        return res;
    }
}
