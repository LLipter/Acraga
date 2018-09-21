package token;

import type.ValueType;

public class Value extends Token {
	
	private ValueType valueType;
	private int intValue;
	private double doubleValue;
	private boolean boolValue;
	
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
	
	public boolean isInt() {
		return valueType == ValueType.INTEGER;
	}
	
	public boolean isDouble() {
		return valueType == ValueType.DOUBLE;
	}
	
	public boolean isBool() {
		return valueType == ValueType.BOOLEAN;
	}
	



}
