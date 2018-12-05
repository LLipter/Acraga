package token.exprtoken;

import component.context.DataStack;
import component.signal.ControlSignal;
import exception.RTException;
import type.TokenType;
import type.ValueType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Value extends ExpressionToken {

    private ValueType valueType;
    private BigInteger intValue;
    private BigDecimal doubleValue;
    private boolean boolValue;
    private String stringValue;

    public Value(ValueType type) {
        super();
        tokenType = TokenType.VALUE;
        valueType = type;
        setDefaultValue();
    }

    public Value(Value otherValue) {
        super(otherValue);
        this.valueType = otherValue.valueType;
        this.intValue = otherValue.intValue;
        this.doubleValue = otherValue.doubleValue;
        this.boolValue = otherValue.boolValue;
        this.stringValue = otherValue.stringValue;
    }

    public BigInteger getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer value) {
        intValue = new BigInteger(value.toString());
    }

    public void setIntValue(BigInteger intValue) {
        this.intValue = intValue;
    }

    public BigDecimal getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double value) {
        doubleValue = new BigDecimal(value.toString());
    }

    public void setDoubleValue(BigDecimal doubleValue) {
        this.doubleValue = doubleValue;
    }

    public boolean getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public boolean isInt() {
        return valueType == ValueType.INTEGER;
    }

    public boolean isDouble() {
        return valueType == ValueType.DOUBLE;
    }

    public boolean isBool() {
        return valueType == ValueType.BOOLEAN;
    }

    public boolean isString() {
        return valueType == ValueType.STRING;
    }

    public boolean isVoid() {
        return valueType == ValueType.VOID;
    }

    public String getValueString() {
        if (isInt())
            return intValue.toString();
        if (isDouble())
            return doubleValue.toString();
        if (isBool())
            return Boolean.toString(boolValue);
        if (isString())
            return String.format("\"%s\"", stringValue);
        if (isVoid())
            return "void";
        return "UnknownValue";
    }

    @Override
    public String toString() {
        if (isInt())
            return String.format("<IntValue,%d>", intValue);
        if (isDouble())
            return String.format("<DoubleValue,%f>", doubleValue);
        if (isBool())
            return String.format("<BoolValue,%b>", boolValue);
        if (isString())
            return String.format("<StringValue,%s>", stringValue);
        if (isVoid())
            return String.format("<VoidValue>");
        return "<UnknownValue>";
    }

    public void setDefaultValue() {
        if (isInt())
            setIntValue(BigInteger.ZERO);
        else if (isDouble())
            setDoubleValue(BigDecimal.ZERO);
        else if (isBool())
            setBoolValue(false);
        else if (isString())
            setStringValue("");
        else if (!isVoid())
            System.err.println("Unknown data type");
    }


    public ValueType getValueType() {
        return valueType;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {
        return this;
    }

    @Override
    public void print(StringBuilder sb, int indent) {
        printWithIndent(sb, indent, this.toString());
    }
}
