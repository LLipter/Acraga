package node;

import java.util.Iterator;
import java.util.LinkedList;

import type.ValueType;

public class FunctionSignature {
	
	private String functionName;
	private LinkedList<ValueType> parameters;
	
	public FunctionSignature(String fName) {
		functionName = fName;
		parameters = new LinkedList<ValueType>();
	}

	public String getFunctionName() {
		return functionName;
	}
	
	public void addParameters(ValueType type) {
		parameters.addLast(type);
	}
	
	

	@Override
	public int hashCode() {
		long hashCode = 0;
		int MOD = 1000000007;
		char[] chs = functionName.toCharArray();
		for(int i=0;i<chs.length;i++) {
			hashCode *= chs[i];
			hashCode %= MOD;
		}
		for(ValueType type : parameters) {
			hashCode *= type.hashCode();
			hashCode %= MOD;
		}
		
		return (int)hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof FunctionSignature))
			return false;
		FunctionSignature fs = (FunctionSignature) obj;
		if(!fs.functionName.equals(functionName))
			return false;
		LinkedList<ValueType> fsParameters = fs.parameters;
		if(fsParameters.size() != parameters.size())
			return false;
		
		Iterator<ValueType> it1 = fsParameters.iterator();
		Iterator<ValueType> it2 = parameters.iterator();
		while(it1.hasNext()) {
			ValueType type1 = it1.next();
			ValueType type2 = it2.next();
			if(type1 != type2)
				return false;
		}
		
		return true;
	}
	
	

}
