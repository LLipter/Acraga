package component.function.predefined.math;

import component.context.DataStack;
import component.function.Function;
import component.function.FunctionSignature;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

import java.math.BigDecimal;
import java.math.BigInteger;


public class min extends Function {
    private ValueType type1;
    private ValueType type2;

    public min(ValueType type1, ValueType type2) {
        FunctionSignature fs = new FunctionSignature("min");
        setId(new Identifier("min"));
        setFunctionSignature(fs);

        this.type1 = type1;
        this.type2 = type2;


        if (type1 == ValueType.INTEGER && type2 == type1) {
            addParameter(ValueType.INTEGER, new Identifier("minParam1"));
            addParameter(ValueType.INTEGER, new Identifier("minParam2"));
        }
        if (type1 == ValueType.DOUBLE && type2 == type1) {
            addParameter(ValueType.DOUBLE, new Identifier("minParam1"));
            addParameter(ValueType.DOUBLE, new Identifier("minParam2"));
        }
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        Value value1 = arguments.get(0);
        Value value2 = arguments.get(1);
        if (type1 == ValueType.INTEGER && type2 == type1) {
            BigInteger bigInteger1 = value1.getIntValue();
            BigInteger bigInteger2 = value2.getIntValue();
            if (bigInteger1.compareTo(bigInteger2) <= 0)
                value1.setIntValue(bigInteger1);
            else
                value1.setIntValue(bigInteger2);
        }
        if (type1 == ValueType.DOUBLE && type2 == type1) {
            BigDecimal bigDecimal1 = value1.getDoubleValue();
            BigDecimal bigDecimal2 = value2.getDoubleValue();
            if (bigDecimal1.compareTo(bigDecimal2) <= 0)
                value1.setDoubleValue(bigDecimal1);
            else
                value1.setDoubleValue(bigDecimal2);
        }
        return value1;
    }
}

