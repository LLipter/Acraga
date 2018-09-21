package token;

public class ReturnValue {
	private DataType dataType;
	private int intValue;
	private double doubleValue;
	private String identifierValue;
	

	public ReturnValue(DataType type) {
		dataType = type;
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
		return dataType == DataType.Integer;
	}
	
	public boolean isDouble() {
		return dataType == DataType.Double;
	}

	public boolean isIdentifier() {
		return dataType == DataType.Identifier;
	}
	
}
