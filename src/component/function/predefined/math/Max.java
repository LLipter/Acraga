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

public class Max extends Predefined {
    private ValueType type;

    public Max(ValueType type) {
        FunctionSignature fs = new FunctionSignature("max");
        setId(new Identifier("max"));
        setFunctionSignature(fs);

        this.type = type;


        if (type == ValueType.INTEGER) {
            addParameter(ValueType.INTEGER, new Identifier("maxParam1"));
            addParameter(ValueType.INTEGER, new Identifier("maxParam2"));
        }
        if (type == ValueType.DOUBLE) {
            addParameter(ValueType.DOUBLE, new Identifier("maxParam1"));
            addParameter(ValueType.DOUBLE, new Identifier("maxParam2"));
        }
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        Value value1 = arguments.get(0);
        Value value2 = arguments.get(1);
        if (type == ValueType.INTEGER) {
            BigInteger bigInteger1 = value1.getIntValue();
            BigInteger bigInteger2 = value2.getIntValue();
            if (bigInteger1.compareTo(bigInteger2) < 0)
                value1.setIntValue(bigInteger2);
        }
        if (type == ValueType.DOUBLE) {
            BigDecimal bigDecimal1 = value1.getDoubleValue();
            BigDecimal bigDecimal2 = value2.getDoubleValue();
            if (bigDecimal1.compareTo(bigDecimal2) < 0)
                value1.setDoubleValue(bigDecimal2);
        }
        return value1;
    }
}
