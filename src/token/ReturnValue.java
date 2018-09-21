package token;

public class ReturnValue {
	private DataType dataType;
	private int intValue;
	private double doubleValue;
	
	public ReturnValue(DataType type) {
		dataType = type;
	}
	
	public void setIntValue(int value) {
		intValue = value;
	}
	
	public void setDoubleValue(double value) {
		doubleValue = value;
	}
	
	public int getIntValue() {
		return intValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public boolean isInt() {
		return dataType == DataType.Integer;
	}
	
	public boolean isDouble() {
		return dataType == DataType.Double;
	}
	
	
}
