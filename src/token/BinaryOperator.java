package token;

public class BinaryOperator extends Operator {
	
	private Token operand1;
	private Token operand2;

	
	public BinaryOperator(OperatorType type) {
		operatorType = type;
	}
	
	public void setOp1(Token op1) {
		operand1 = op1;
	}
	
	public void setOp2(Token op2) {
		operand2 = op2;
	}
	

	@Override
	public ReturnValue run() {
		
		ReturnValue leftValue = operand1.run();
		ReturnValue rightValue = operand2.run();
		
		if(leftValue.isError())
			return leftValue;
		if(rightValue.isError())
			return rightValue;
		
		
		ReturnValue ret;
		if(leftValue.isDouble() || rightValue.isDouble()) {
			ret = new ReturnValue(ValueType.Double);
			double result;
			double op1;
			double op2;
			if(leftValue.isDouble())
				op1 = leftValue.getDoubleValue();
			else
				op1 = (double) leftValue.getDoubleValue();
			if(rightValue.isDouble())
				op2 = rightValue.getDoubleValue();
			else
				op2 = (double) rightValue.getDoubleValue();
			if(operatorType == OperatorType.Add)
				result = op1 + op2;
			else if(operatorType == OperatorType.Subtract)
				result = op1 - op2;
			else if(operatorType == OperatorType.Multiply)
				result = op1 * op2;
			else if(operatorType == OperatorType.Divide) {
				if(op2 == 0) {
					ret = new ReturnValue(ValueType.RuntimeError);
					ret.setErrorMsg("divided by zero");
					return ret;
				}
				result = op1 / op2;
			}
			else {
				ret = new ReturnValue(ValueType.RuntimeError);
				ret.setErrorMsg("unknown binary operator type");
				return ret;
			}
			
			ret.setDoubleValue(result);
		}else {
			ret = new ReturnValue(ValueType.Integer);
			int result;
			int op1 = leftValue.getIntValue();
			int op2 = rightValue.getIntValue();
			
			if(operatorType == OperatorType.Add)
				result = op1 + op2;
			else if(operatorType == OperatorType.Subtract)
				result = op1 - op2;
			else if(operatorType == OperatorType.Multiply)
				result = op1 * op2;
			else if(operatorType == OperatorType.Divide) {
				if(op2 == 0) {
					ret = new ReturnValue(ValueType.RuntimeError);
					ret.setErrorMsg("divided by zero");
					return ret;
				}
				result = op1 / op2;
			}
			else {
				ret = new ReturnValue(ValueType.RuntimeError);
				ret.setErrorMsg("unknown binary operator type");
				return ret;
			}
			
			ret.setIntValue(result);
		}
		
		
		return ret;
	}
	
}
