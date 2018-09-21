package token;

import type.ValueType;

public class Value extends Token {
	
	private ReturnValue retValue;
	
	public Value(ValueType type) {
		retValue = new ReturnValue(type);
	}
	
	public void setIntValue(int value) {
		retValue.setIntValue(value);
	}
	
	public void setDoubleValue(double value) {
		retValue.setDoubleValue(value);
	}
	
	public void setIdentifierValue(String identifier) {
		retValue.setIdentifierValue(identifier);
	}
	
	public void setErrorMsg(String msg) {
		retValue.setErrorMsg(msg);
	}

	@Override
	public ReturnValue run() {
		return retValue;
	}

}
