package node;

import token.Value;
import type.NodeType;
import type.ValueType;

public class Function extends Node {
	
	private FunctionSignature functionSignature;
	private ValueType returnType;
	
	public Function(String functionName, ValueType type) {
		nodeType = NodeType.FUNCTION;
		functionSignature = new FunctionSignature(functionName);
		returnType = type;
	}
	
	public void addParameter(ValueType type) {
		functionSignature.addParameters(type);
	}

	public FunctionSignature getFunctionSignature() {
		return functionSignature;
	}

	public ValueType getReturnType() {
		return returnType;
	}
	
	public Value run() {
		// TODO:
		return null;
	}
}
