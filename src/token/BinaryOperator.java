package token;

import type.OperatorType;
import type.ValueType;

public class BinaryOperator extends Operator {
	
	private Token operand1;
	private Token operand2;

	public BinaryOperator(OperatorType type) {
		super();
		operatorType = type;
	}
	
	public void setOp1(Token op1) {
		operand1 = op1;
	}
	
	public void setOp2(Token op2) {
		operand2 = op2;
	}
	
	@Override
	public String toString() {
		return String.format("<BinaryOperator,%s>", operatorType);
	}
	
}
