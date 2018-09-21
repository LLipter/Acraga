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
	ReturnValue run() {
		
		ReturnValue leftValue = operand1.run();
		ReturnValue rightValue = operand2.run();
		
		ReturnValue ret;
		if(leftValue.isDouble() || rightValue.isDouble()) {
			ret = new ReturnValue(DataType.Double);
			double result = 0;
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
			else if(operatorType == OperatorType.Divide)
				result = op1 / op2;
			else {
				System.err.println("Binary Operator : unknown operator type");
				System.exit(1);
			}
			
			ret.setDoubleValue(result);
		}else {
			ret = new ReturnValue(DataType.Integer);
			int result = 0;
			int op1 = leftValue.getIntValue();
			int op2 = rightValue.getIntValue();
			
			if(operatorType == OperatorType.Add)
				result = op1 + op2;
			else if(operatorType == OperatorType.Subtract)
				result = op1 - op2;
			else if(operatorType == OperatorType.Multiply)
				result = op1 * op2;
			else if(operatorType == OperatorType.Divide)
				result = op1 / op2;
			else {
				System.err.println("Binary Operator : unknown operator type");
				System.exit(1);
			}
			
			ret.setIntValue(result);
		}
		
		
		return ret;
	}
	
}
