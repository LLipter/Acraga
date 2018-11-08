package token.exprtoken.operator.binary;

import component.signal.ControlSignal;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.OperatorType;
import type.TokenType;

public class Assign extends BinaryOperator {
    public Assign() {
        operatorType = OperatorType.ASSIGN;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {
        if (lChild.getTokenType() != TokenType.IDENTIFIER)
            throw new RTException(getLines(), getPos(), "left value required");
        Value value = rChild.execute(context);
        lChild.execute(context);
        if (value.isVoid())
            throw new RTException(getLines(), getPos(), "cannot be assigned with void");
        context.setValue((Identifier) lChild, value);
        return value;
    }
}
