package component.function;

import type.ValueType;

import java.util.Iterator;
import java.util.LinkedList;

public class FunctionSignature {

    public static FunctionSignature mainFunctionSignature = new FunctionSignature("main");
    private String functionName;
    private LinkedList<Parameter> parameters;

    public FunctionSignature(String fName) {
        functionName = fName;
        parameters = new LinkedList<Parameter>();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void addParameters(ValueType type, String name) {
        parameters.addLast(new Parameter(type, name));
    }

    @Override
    public int hashCode() {
        long hashCode = 0;
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
        LinkedList<Parameter> fsParameters = fs.parameters;
        if (fsParameters.size() != parameters.size())
            return false;

        Iterator<Parameter> it1 = fsParameters.iterator();
        Iterator<Parameter> it2 = parameters.iterator();
        while (it1.hasNext()) {
            Parameter type1 = it1.next();
            Parameter type2 = it2.next();
            if (type1.getDataType() != type2.getDataType())
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
