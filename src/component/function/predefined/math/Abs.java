package component.function.predefined.math;

import component.context.DataStack;
import component.function.predefined.Predefined;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Abs extends Predefined {
    private ValueType type;

    public Abs(ValueType type) {
        super(new Identifier("abs"), type);

        this.type = type;
        if (type == ValueType.INTEGER)
            addParameter(ValueType.INTEGER, new Identifier("absParam"));

        if (type == ValueType.DOUBLE)
            addParameter(ValueType.DOUBLE, new Identifier("absParam"));
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        Value value = arguments.get(0);
        BigInteger bigInteger;
        BigDecimal bigDecimal;
        if (type == ValueType.INTEGER) {
            bigInteger = value.getIntValue();
            if (bigInteger.compareTo(BigInteger.ZERO) < 0)
                value.setIntValue(bigInteger.negate());
            else
                value.setIntValue(bigInteger);
        }
        if (type == ValueType.DOUBLE) {
            bigDecimal = value.getDoubleValue();
            if (bigDecimal.compareTo(BigDecimal.ZERO) < 0)
                value.setDoubleValue(bigDecimal.negate());
            else
                value.setDoubleValue(bigDecimal);
        }
        return value;
    }

}
