package token;

import type.ValueType;

public class ReturnValue {
	private ValueType valueType;
	private int intValue;
	private double doubleValue;
	private String identifierValue;
	private String errorMsg;
	

	public ReturnValue(ValueType type) {
		valueType = type;
	}
	
	public void setIntValue(int value) {
		intValue = value;
	}
	
	public void setDoubleValue(double value) {
		doubleValue = value;
	}

	public void setIdentifierValue(String identifierValue) {
		this.identifierValue = identifierValue;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public int getIntValue() {
		return intValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}
	
	public String getIdentifierValue() {
		return identifierValue;
	}

	public boolean isInt() {
		return valueType == ValueType.Integer;
	}
	
	public boolean isDouble() {
		return valueType == ValueType.Double;
	}

	public boolean isIdentifier() {
		return valueType == ValueType.Identifier;
	}
	
	public boolean isError() {
		return valueType == ValueType.RuntimeError;
	}
	
}
