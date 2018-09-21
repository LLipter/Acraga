package token;

import type.ValueType;

public class Value extends Token {
	
	private ValueType valueType;
	private int intValue;
	private double doubleValue;
	private boolean boolValue;
	private String stringValue;
	
	public Value(ValueType type) {
		valueType = type;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public boolean isBoolValue() {
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
	
	@Override
	public String toString() {
		if(isInt())
			return String.format("<IntValue,%d>", intValue);
		if(isDouble())
			return String.format("<DoubleValue,%f>", doubleValue);
		if(isBool())
			return String.format("<BoolValue,%b>", boolValue);
		if(isString())
			return String.format("<StringValue,%s>", stringValue);
		return "<UnknownValue>";
	}
	



}
