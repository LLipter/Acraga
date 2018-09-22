package node;

import type.NodeType;
import type.ValueType;

public class Function extends Node {
	
	private FunctionSignature functionSignature;
	
	public Function(String functionName) {
		nodeType = NodeType.FUNCTION;
		functionSignature = new FunctionSignature(functionName);
	}
	
	public void addParameter(ValueType type) {
		functionSignature.addParameters(type);
	}
	
}
