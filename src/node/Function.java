package node;

import token.Value;
import type.NodeType;
import type.ValueType;

import java.util.Iterator;
import java.util.LinkedList;

public class Function extends Node implements Iterable<Statement>{
	
	private FunctionSignature functionSignature;
	private ValueType returnType;
	private LinkedList<Statement> statements;
	
	public Function(String functionName, ValueType type) {
		nodeType = NodeType.FUNCTION;
		functionSignature = new FunctionSignature(functionName);
		returnType = type;
		statements = new LinkedList<Statement>();
	}
	
	public void addParameter(ValueType type, String name) {
		functionSignature.addParameters(type, name);
	}

	public FunctionSignature getFunctionSignature() {
		return functionSignature;
	}

	public ValueType getReturnType() {
		return returnType;
	}

	public void addStatement(Statement statement){
		statements.addLast(statement);
	}

	@Override
	public Iterator<Statement> iterator() {
		return statements.iterator();
	}
}
