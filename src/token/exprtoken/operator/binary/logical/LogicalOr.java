package token.exprtoken.operator.binary.logical;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.operator.binary.BinaryOperator;
import type.Casting;
import type.OperatorType;
import type.ValueType;

public class LogicalOr extends BinaryOperator {

    public LogicalOr(){
        operatorType = OperatorType.LOGICALOR;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        Value lvalue = lChild.execute(context);
        Value rvalue = rChild.execute(context);

        Value res = new Value(ValueType.BOOLEAN);
        if(lvalue.isVoid() || rvalue.isVoid())
            throw new RTException(getLines(), getPos(), "void variable is not allowed to do operation");
        boolean bool1 = Casting.casting(lvalue, ValueType.BOOLEAN).getBoolValue();
        boolean bool2 = Casting.casting(rvalue, ValueType.BOOLEAN).getBoolValue();
        res.setBoolValue(bool1 || bool2);
        return res;
    }
}
