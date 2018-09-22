package node;

import type.ValueType;

public class Parameter {
	
	private ValueType dataType;
	private String parameterName;
	
	public Parameter(ValueType dataType, String parameterName) {
		super();
		this.dataType = dataType;
		this.parameterName = parameterName;
	}
	public ValueType getDataType() {
		return dataType;
	}
	public void setDataType(ValueType dataType) {
		this.dataType = dataType;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	
	

}
