package component.function.predefined.math;

import component.context.DataStack;
import component.function.FunctionSignature;
import component.function.predefined.Predefined;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

import java.math.BigDecimal;
import java.math.BigInteger;


public class Min extends Predefined {
    private ValueType type;

    public Min(ValueType type) {
        FunctionSignature fs = new FunctionSignature("min");
        setId(new Identifier("min"));
        setFunctionSignature(fs);
        this.type = type;

        if (type == ValueType.INTEGER) {
            addParameter(ValueType.INTEGER, new Identifier("minParam1"));
            addParameter(ValueType.INTEGER, new Identifier("minParam2"));
        } else if (type == ValueType.DOUBLE) {
            addParameter(ValueType.DOUBLE, new Identifier("minParam1"));
            addParameter(ValueType.DOUBLE, new Identifier("minParam2"));
        }
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        Value value1 = arguments.get(0);
        Value value2 = arguments.get(1);
        if (type == ValueType.INTEGER) {
            BigInteger bigInteger1 = value1.getIntValue();
            BigInteger bigInteger2 = value2.getIntValue();
            if (bigInteger1.compareTo(bigInteger2) > 0)
                value1.setIntValue(bigInteger2);
        } else if (type == ValueType.DOUBLE) {
            BigDecimal bigDecimal1 = value1.getDoubleValue();
            BigDecimal bigDecimal2 = value2.getDoubleValue();
            if (bigDecimal1.compareTo(bigDecimal2) > 0)
                value1.setDoubleValue(bigDecimal2);
        }
        return value1;
    }
}

