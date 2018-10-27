package token;

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
        tokenType = TokenType.VALUE;
        valueType = type;
        setDefaultValue();
    }

    public BigInteger getIntValue() {
        return intValue;
    }

    public void setIntValue(BigInteger intValue) {
        this.intValue = intValue;
    }

    public BigDecimal getDoubleValue() {
        return doubleValue;
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

    public void setIntValue(Integer value){
        intValue = new BigInteger(value.toString());
    }

    public void setDoubleValue(Double value){
        doubleValue = new BigDecimal(value.toString());
    }

    @Override
    public String toString() {
        if (isInt())
            return String.format("<IntValue,%d>", intValue);
        if (isDouble())
            return String.format("<DoubleValue,%.15f>", doubleValue);
        if (isBool())
            return String.format("<BoolValue,%b>", boolValue);
        if (isString())
            return String.format("<StringValue,%s>", stringValue);
        return "<UnknownValue>";
    }

    public void setDefaultValue(){
        if(isInt())
            setIntValue(BigInteger.ZERO);
        else if(isDouble())
            setDoubleValue(BigDecimal.ZERO);
        else if(isBool())
            setBoolValue(false);
        else if(isString())
            setStringValue("");
        else
            System.err.println("Unknown data type");
    }


}
