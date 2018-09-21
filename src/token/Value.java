package token;

public class Value extends Token {
	
	private ReturnValue retValue;
	
	public Value(DataType type) {
		retValue = new ReturnValue(type);
	}
	
	public void setIntValue(int value) {
		retValue.setIntValue(value);
	}
	
	public void setDoubleValue(double value) {
		retValue.setDoubleValue(value);
	}

	@Override
	ReturnValue run() {
		return retValue;
	}

}
