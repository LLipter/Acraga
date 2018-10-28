package node;

import node.statement.Statement;
import type.ValueType;

import java.util.Iterator;
import java.util.LinkedList;

public class Function implements Iterable<Statement> {

    private FunctionSignature functionSignature;
    private ValueType returnType;
    private LinkedList<Statement> statements;

    public Function(String functionName, ValueType type) {
        functionSignature = new FunctionSignature(functionName);
        returnType = type;
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

    public LinkedList<Statement> getStatements() {
        return statements;
    }

    public void setStatements(LinkedList<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public Iterator<Statement> iterator() {
        return statements.iterator();
    }
}
