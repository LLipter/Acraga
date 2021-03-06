package component.function;

import token.exprtoken.identifier.Identifier;
import type.ValueType;

import java.util.ArrayList;
import java.util.Iterator;

public class FunctionSignature {

    public static FunctionSignature mainFunctionSignature = new FunctionSignature("main");
    private String functionName;
    private ArrayList<Parameter> parameters;

    public FunctionSignature(String fName) {
        functionName = fName;
        parameters = new ArrayList<>();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void addParameters(ValueType type, Identifier pid) {
        parameters.add(new Parameter(type, pid));
    }

    public void addParameters(ValueType type) {
        parameters.add(new Parameter(type));
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public int hashCode() {
        long hashCode = 1;
        int MOD = 1000000007;
        char[] chs = functionName.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            hashCode *= chs[i];
            hashCode %= MOD;
        }
        for (Parameter parameter : parameters) {
            hashCode *= parameter.getDataType().hashCode();
            hashCode %= MOD;
        }

        return (int) hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FunctionSignature))
            return false;
        FunctionSignature fs = (FunctionSignature) obj;
        if (!fs.functionName.equals(functionName))
            return false;
        ArrayList<Parameter> fsParameters = fs.parameters;
        if (fsParameters.size() != parameters.size())
            return false;

        for (int i = 0; i < parameters.size(); i++) {
            Parameter pa1 = parameters.get(i);
            Parameter pa2 = fsParameters.get(i);
            if (pa1.getDataType() != pa2.getDataType())
                return false;
        }

        return true;
    }

    @Override
    public String toString() {
        if (parameters.size() == 0)
            return String.format("%s()", functionName);
        else if (parameters.size() == 1)
            return String.format("%s(%s)", functionName, parameters.get(0));
        else {
            StringBuffer sb = new StringBuffer();
            sb.append(functionName);
            sb.append("(");
            Iterator<Parameter> it = parameters.iterator();
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(", ");
                sb.append(it.next());
            }
            sb.append(")");
            return sb.toString();
        }

    }
}
